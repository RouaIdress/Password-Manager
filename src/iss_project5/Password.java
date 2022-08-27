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
public class Password implements Serializable{
    int id;
    int user_id ;
    String password = "";
    String userName = "";
    String title = "";
    byte[] file = null;
    String description = "";
    
    void Encode_Password(String key) throws Exception {
        if(!this.password.equals("")){
            this.password = SymmetricWithMac.encrypt(this.password,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
        if(!this.title.equals("")){
            this.title = SymmetricWithMac.encrypt(this.title,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
        if(!this.description.equals("")){
            this.description = SymmetricWithMac.encrypt(this.description,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
        if(!this.userName.equals("")){
            this.userName = SymmetricWithMac.encrypt(this.userName,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
    }
    void Decode_Password(String key) throws Exception {
        if(!this.password.equals("")){
            this.password = SymmetricWithMac.decrypt(this.password,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
        if(!this.title.equals("")){
            this.title = SymmetricWithMac.decrypt(this.title,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
        if(!this.description.equals("")){
            this.description = SymmetricWithMac.decrypt(this.description,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
        if(!this.userName.equals("")){
            this.userName = SymmetricWithMac.decrypt(this.userName,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        }
    }

    void RSA_Encode_Password(PublicKey publicKey) throws Exception {
        if(!this.password.equals("")){
            this.password = RSA.encrypted(this.password,publicKey);
        }
        if(!this.title.equals("")){
            this.title = RSA.encrypted(this.title,publicKey);
        }
        if(!this.description.equals("")){
            this.description = RSA.encrypted(this.description,publicKey);
        }
        if(!this.userName.equals("")){
            this.userName = RSA.encrypted(this.userName,publicKey);
        }
    }
    void RSA_Decode_Password(PrivateKey privateKey) throws Exception {
        if(this.password!= null){
            this.password = RSA.decrypted(this.password,privateKey);
        }
        if(!this.title.equals("")){
            this.title = RSA.decrypted(this.title,privateKey);
        }
        if(!this.description.equals("")){
            this.description = RSA.decrypted(this.description,privateKey);
        }
        if(!this.userName.equals("")){
            this.userName = RSA.decrypted(this.userName,privateKey);
        }
    }

    @Override
    public String toString() {        
        return "Password:" + "\nId= " + id + "\nUser_id= " + user_id + "\nPassword= " + password + "\nUser Name=" + userName + "\nTitle=" + title + "\nDescription=" + description + '}';
    }
    
}

