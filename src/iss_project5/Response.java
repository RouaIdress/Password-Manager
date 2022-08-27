/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iss_project5;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 *
 * @author Bcc
 */
public class Response implements Serializable {
    String msg = "";
    User user =null;
    Password password =null;
    void Encode(String key) throws Exception {
        this.msg = Encode_Message(this.msg,key);
        if(this.user!=null){
            this.user.Encode_Password(key);
        }
        if(this.password!=null){
            this.password.Encode_Password(key);
        }
    }
    void Decode(String key) throws Exception{
        this.msg =Decode_Message(this.msg,key);
        if(this.user!= null){
            this.user.Decode_Password(key);
        }
        if(this.password!= null){
            this.password.Decode_Password(key);
        }
    }
    String Encode_Message(String message,String key) throws Exception {
        return SymmetricWithMac.encrypt(message,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
    }
    String Decode_Message(String message, String key) throws Exception {
        return SymmetricWithMac.decrypt(message, key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
    }
    void RSA_Decode(PrivateKey privateKey) throws Exception {
        if(this.user!= null){
            this.user.RSA_Decode_User(privateKey);
        }
        if(this.password!= null){
            this.password.RSA_Decode_Password(privateKey);
        }
    }

    @Override
    public String toString() {
        return "Response{" + "msg=" + msg + ", user=" + user + ", password=" + password + '}';
    }
    
    
    
}
