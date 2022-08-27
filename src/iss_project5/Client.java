package iss_project5;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import java.security.*;
import javax.swing.JOptionPane;

public class Client {
    static Socket socket;
    static Response response = new Response();
    static Request request = new Request();
    static User user = new User();
    static PublicKey ServerPublicKey;
    static String sessionKey;
    static int type;
    
    public static void main(String[] args) throws Exception {
        socket = new Socket("localhost", 7777);
        ask_for_serverKey(socket);
        send_key(socket);
        Vsiter.main(args);
    }
    static void send_request(Request req) throws Exception {
        request= req;
        System.out.println(request);
        type= request.type;
        user.publicKey = RSA.takePublicKey(RSA.PATH_PUBLIC_KEY);
        user.privateKey = RSA.takePrivateKey(RSA.PATH_PRIVATE_KEY);
//        if(type!=1){
//            user.publicKey = RSA.takePublicKey(RSA.PATH_PUBLIC_KEY);
//            user.privateKey = RSA.takePrivateKey(RSA.PATH_PRIVATE_KEY);
//        }
//        if(type==1){
//            
//            request.user.publicKey = RSA.takePublicKey(RSA.PATH_PUBLIC_KEY);
//            user.publicKey = RSA.takePublicKey(RSA.PATH_PUBLIC_KEY);
//        }
        if(type==6){
            Password deletePassword = find_password(request.password);
            if(deletePassword!=null){
                request.password = deletePassword;
            }
        }
        request.RSA_Encode(RSA.takePublicKey(RSA.PATH_PUBLIC_KEY));
        String signature = request.request_signature(RSA.takePrivateKey(RSA.PATH_PRIVATE_KEY));
        request.Encode(sessionKey);
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos= new ObjectOutputStream(os);
        oos.writeObject(request);
        oos.writeObject(signature);
        if(type==1||type==2){
            send_myPublicKey(socket);
        }
        if(type == 5)
        {
            EditPassword.password = find_password(EditPassword.password);
            if(EditPassword.password!=null){
                EditPassword.password.RSA_Encode_Password(user.publicKey);
            }
            oos.writeObject(EditPassword.password);
        }
        InputStream in = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);
        try{
            response = (Response)ois.readObject();
            processResponse(response);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    static void processResponse(Response response) throws Exception {

        response.Decode(sessionKey);
        if(response.msg.equals("success") && type == 1)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            System.out.println("Response "+response.user);
            user = response.user;
//            user.publicKey = RSA.takePublicKey(RSA.PATH_PUBLIC_KEY);
//            user.privateKey = RSA.takePrivateKey(RSA.PATH_PRIVATE_KEY);
            signup.next_step();
        }
        else if(response.msg.equals("fail") && type == 1)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            signup.same_step();
        }
        if(response.msg.equals("success") && type == 2)
        {
            response.user.RSA_Decode_User(RSA.takePrivateKey(RSA.PATH_PRIVATE_KEY));
            if(RightPasswordAndUserName(response.user)){
                JOptionPane.showMessageDialog(null, response.msg);
                response.user.publicKey = RSA.takePublicKey(RSA.PATH_PUBLIC_KEY);
                response.user.privateKey = RSA.takePrivateKey(RSA.PATH_PRIVATE_KEY);
                response.user.myPassword = getClientPasswords(response.user.userId);
                user = response.user;
                System.out.println("Response "+response.user);
                System.out.println(user.myPassword);
                Login.next_step();
            }else{
                JOptionPane.showMessageDialog(null, "Wrong Password or Username");
                Login.same_step();
            }

        }
        else if(response.msg.equals("fail") && type == 2)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            Login.same_step();
        }
        if(response.msg.equals("success") && type == 3)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            user.myPassword = getClientPasswords(user.userId);
            System.out.println(user.myPassword);
            AddPassword.next_step();
        }
        else if(response.msg.equals("fail") && type == 3)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            AddPassword.same_step();
        }
        if(type == 4)
        {
            response.password.RSA_Decode_Password(user.privateKey);
            Password mySelectedPassword = find_password(response.password);
            System.out.println(response.password);
            if(mySelectedPassword!= null){
                JOptionPane.showMessageDialog(null, mySelectedPassword);
                if(mySelectedPassword.file !=null)
                {
                    OutputStream os = new FileOutputStream("C:\\Users\\Bcc\\Desktop\\new1.pdf");
                    os.write(mySelectedPassword.file);
                    os.close();
                }
                ShowPassword.next_step();
            }else {
                ShowPassword.same_step();
                JOptionPane.showMessageDialog(null, "fail");
            }
        }
        if(response.msg.equals("success") && type == 5)
        {
            response.password.RSA_Decode_Password(user.privateKey);
            JOptionPane.showMessageDialog(null, response.msg+"\n"+response.password);
            user.myPassword = getClientPasswords(user.userId);
            System.out.println(user.myPassword);
            EditPassword.next_step();
        }
        else if(response.msg.equals("fail") && type == 5)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            EditPassword.same_step();
        }
        if(response.msg.equals("success") && type == 6)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            user.myPassword = getClientPasswords(user.userId);
            System.out.println(user.myPassword);
            DeletePassword.next_step();
        }
        else if(response.msg.equals("fail") && type == 6)
        {
            JOptionPane.showMessageDialog(null, response.msg);
            DeletePassword.same_step();
        }
        if(response.msg.equals("success") && type == 7)
        {
            //read other user public key from response
            PublicKey userPublicKey = response.user.publicKey;
            //uncode password in request
            request.Decode(sessionKey);
            request.password.RSA_Decode_Password(user.privateKey);
            //find password in my passwords arraylist
            Password pass = find_password(request.password);
            //encode my password with other user public key
            pass.RSA_Encode_Password(userPublicKey);
            request.password = pass;
            //creat another request and intilize it with the new password then encode it
            request.Encode(sessionKey);
            //send to server
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos= new ObjectOutputStream(os);
            oos.writeObject(request);
            //read the response
            InputStream in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            response = (Response)ois.readObject();
            response.Decode(sessionKey);
            JOptionPane.showMessageDialog(null, response.msg);
            if(response.msg.equals("success"))
            {
                AddRequest.next_step();
            }
            else
            {
                AddRequest.same_step();
            }
        }
        else if(response.msg.equals("fail") && type == 7)
        {
            JOptionPane.showMessageDialog(null, response.msg+" no such user");
            AddRequest.same_step();
        }
        if(response.msg.equals("success") && (type == 8||type == 9))
        {
            JOptionPane.showMessageDialog(null, response.msg);
            ShowRequest.next_step();
        }
        else if(response.msg.equals("fail") && (type == 8||type == 9))
        {
            JOptionPane.showMessageDialog(null, response.msg);
            ShowRequest.same_step();
        }
    }
    
    static ArrayList<Password> getClientPasswords(int userID) throws Exception {
        ArrayList<Password> clientPasswords = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/iss_project", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM passwords");
            while(rs.next())
            {
                int user_id=rs.getInt(2);
                String title = rs.getString(3);
                String pass = rs.getString(5);
                if(user_id == userID)
                {
                    Password password = new Password();
                    password.id = rs.getInt(1);
                    password.user_id = user_id;
                    password.title = title;
                    password.userName = rs.getString(4);
                    password.password = pass;
                    password.description = rs.getString(6);
                    Blob blob = rs.getBlob(7);
                    if(blob!=null)
                    {
                        int blobLength = (int) blob.length();
                        password.file = blob.getBytes(1, blobLength);
                        blob.free();
                    }
                    clientPasswords.add(password);
                }
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        for (Password p:clientPasswords) {
            p.RSA_Decode_Password(user.privateKey);
        }
        return clientPasswords;
    }
    
    static void send_myPublicKey(Socket socket) throws Exception {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(user.publicKey);
        System.out.println("send my Public key");
    }
    
    static Password find_password(Password password) {
        for (Password p: user.myPassword) {
            if(p.title.equals(password.title)&&p.password.equals(password.password)){
                return p;
            }
            if(p.title.equals(password.title))
            {
                return p;
            }
        }
        return null;
    }
    
    static boolean RightPasswordAndUserName(User user2){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/iss_project", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while(rs.next())
            {
                int id=rs.getInt(1);

                if(id==user2.userId)
                {
                    String userName = rs.getString(2);
                    String password = rs.getString(3);
                    if(RSA.decodeString(userName,user.privateKey).equals(user2.userName)&&RSA.decodeString(password,user.privateKey).equals(user2.password)){
                        return true;
                    }
                    break;
                }
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    static void send_key(Socket socket) throws Exception {
        sessionKey = sessionKey.substring(0, 16);
        String encryptedSessionKey = RSA.encrypted(sessionKey,ServerPublicKey);
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(encryptedSessionKey);
        System.out.println("send key");
    }
    static void ask_for_serverKey(Socket socket) throws Exception {
        sessionKey = RSA.generateSecretKey();
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String path_of_public_key = (String) ois.readObject();
        ServerPublicKey = RSA.takePublicKey(path_of_public_key);
        System.out.println("ask for server Key");
    }

}