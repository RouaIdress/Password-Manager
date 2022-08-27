package iss_project5;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Server{
    static Request request = new Request();
    static Response response = new Response();
    static Password password =null;
    static ArrayList SharedPasswordList;
    private static PublicKey clientPublicKey;
    private static final String PATH_PRIVATE_KEY = "ServerPrivate.key";
    private static final String PATH_PUBLIC_KEY = "ServerPublic.key";
    private static String sessionKey="";
    static int check=0;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(7777);
        System.out.println("ServerSocket awaiting connections...");
        Socket socket = ss.accept();
        System.out.println("Connected");
        while(true)
        {
            InputStream in = socket.getInputStream();  
            try{
                if(check==0){
                    send_key(socket);
                    ask_for_clientKey(socket);
                    check=1;
                }
                ObjectInputStream ois = new ObjectInputStream(in);
                request = (Request)ois.readObject();
                String signature = (String)ois.readObject();
                request.Decode(sessionKey);
                if(request.type==1||request.type==2){
                    ask_for_clientPublicKey(socket);
                }
                if(request.verify(signature,clientPublicKey)){
                    System.out.println("safe request");
                    if(request.type == 5)
                    {
                        password = (Password)ois.readObject();
                    }
                    if(request.type == 7)
                    {
                        //send public key
                        Response r= Server.getPublicKey(request.user.userId);
                        r.Encode(sessionKey);
                        OutputStream os = socket.getOutputStream();
                        ObjectOutputStream oos= new ObjectOutputStream(os);
                        oos.writeObject(r);
                        System.out.println("send first response in request 7");
                        //read add request and decode it
                        in = socket.getInputStream();
                        ois = new ObjectInputStream(in);
                        request = (Request)ois.readObject();
                        request.Decode(sessionKey);
                    }
                    check(request);
                    if(request.type>0)
                    {
                        OutputStream os = socket.getOutputStream();
                        ObjectOutputStream oos= new ObjectOutputStream(os);
                        oos.writeObject(response);
                    }
                    else
                    {
                        OutputStream os = socket.getOutputStream();
                        ObjectOutputStream oos= new ObjectOutputStream(os);
                        oos.writeObject(SharedPasswordList);
                    }
                }else
                    System.out.println("unsafe request");

            }catch(Exception e)
            {
                System.out.println(e);
                in.close();
                socket.close();
                break;
            }
        }
        ss.close();
    }
    public static void check(Request req) throws Exception{
        if(req.type==-1)
        {
            SharedPasswordList = AddRequest.myRequests(req);
        }
        if(req.type==-2)
        {
            SharedPasswordList =  ShowRequest.myRequests(req);
        }
        if(req.type == 1)
        {
            response = signup.process(req.user);
        }
        else if(req.type == 2)
        {
            response = Login.process(req.user);
        }
        else if(req.type == 3)
        {
            response = AddPassword.process(req.password);
        }
        else if(req.type == 4)
        {
            response = ShowPassword.process(req.password);
        }
        else if(req.type == 5)
        {
            response = EditPassword.process(password,req.password);
        }
        else if(req.type == 6)
        {
            response = DeletePassword.process(req.password);
        }
        else if(req.type == 7)
        {
            response = AddRequest.process(request);
        }
        else if(req.type == 8 || req.type == 9)
        {
            response = ShowRequest.process(request);
        }
        if(req.type>0)
        {
            response.Encode(sessionKey);
        }
    }
    static void ask_for_clientPublicKey(Socket socket) throws Exception {
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        clientPublicKey = (PublicKey) ois.readObject();
        System.out.println("ask for client public Key");
    }
    
    static void generateKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        try{
            FileOutputStream fos = new FileOutputStream(PATH_PUBLIC_KEY);
            fos.write(publicKey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            FileOutputStream fos = new FileOutputStream(PATH_PRIVATE_KEY);
            fos.write(privateKey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void send_key(Socket socket) throws NoSuchAlgorithmException, IOException
    {
        generateKeys();
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(PATH_PUBLIC_KEY);
        System.out.println("send Key");
    }

    static void ask_for_clientKey(Socket socket) throws Exception
    {
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String encryptedSessionKey = (String) ois.readObject();
        PrivateKey privateKey = RSA.takePrivateKey(PATH_PRIVATE_KEY);
        sessionKey = RSA.decrypted(encryptedSessionKey, privateKey);
        System.out.println("ask for client Key");
    }
    
    static Response getPublicKey(int id) throws Exception {
        Response response = new Response();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/iss_project", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            int i=0;
            while(rs.next())
            {
                if(id == rs.getInt(1))
                {
                    System.out.println("\n\n\nrs.getInt(1): "+rs.getInt(1));
                    User user = new User();
                    String s= rs.getString(4);
                    System.out.println("public key: "+s+"\n\n\n");
                    user.publicKey =  RSA.StringToPublicKey(s);
                    response.user = user;
                    i=1;
                    break;
                }
            }
            con.close();
            if(i==1)
            {
                response.msg = "success";
            }
            else
            {
                response.msg = "fail";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("get public key response: "+response);
        return response;
    }
}