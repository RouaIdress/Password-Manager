/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iss_project5;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author Bcc
 */
public class Request implements Serializable{
    int type;
    User user =null;
    Password password = null;
    
    String hash_type = "";
    void Encode(String key) throws Exception {
        hash_type = Encode_INT(this.type,key);
        this.type = 0;
        if(this.user!= null){
            this.user.Encode_Password(key);
        }
        if(this.password!=null){
            this.password.Encode_Password(key);
        }
    }
    void Decode(String key) throws Exception{
        this.type =Decode_INT(hash_type,key);
        this.hash_type = "";
        if(this.user!= null){
            this.user.Decode_Password(key);
        }
        if(this.password!= null){
            this.password.Decode_Password(key);
        }
    }
    String Encode_INT(int id,String key) throws Exception {
        return SymmetricWithMac.encrypt(String.valueOf(id),key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
    }
    int Decode_INT(String hash_id,String key) throws Exception {
        return Integer.parseInt(SymmetricWithMac.decrypt(hash_id, key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM));
    }

    String request_signature(PrivateKey privateKey) throws Exception {
        String signature;
        signature = RSA.sign(this.toString(),privateKey);
        return signature;
    }
    boolean verify(String signature,PublicKey publicKey) throws Exception {
        return RSA.verify(this.toString(),signature,publicKey);
    }
    void RSA_Encode(PublicKey publicKey) throws Exception {
        if(this.user!= null){
            this.user.RSA_Encode_User(publicKey);
        }
        if(this.password!=null){
            this.password.RSA_Encode_Password(publicKey);
        }
    }

    @Override
    public String toString() {
        return "Request{" + "type=" + type + ", user=" + user + ", password=" + password + '}';
    }
        
}