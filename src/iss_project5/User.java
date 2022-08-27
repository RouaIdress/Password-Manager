/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iss_project5;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 *
 * @author Bcc
 */
public class User implements Serializable {
    String userName = "";
    String password ;
    int userId ;
    PublicKey publicKey;
    PrivateKey privateKey;
    ArrayList<Password> myPassword = new ArrayList<>();
    String hashed_userid = "";
    
    void Encode_Password(String key) throws Exception {
        this.password = SymmetricWithMac.encrypt(this.password,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        hashed_userid = SymmetricWithMac.encrypt(String.valueOf(this.userId),key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        this.userName = SymmetricWithMac.encrypt(this.userName,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
    }
    void Decode_Password(String key) throws Exception {
        this.password = SymmetricWithMac.decrypt(this.password,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
        this.userId = Integer.parseInt(SymmetricWithMac.decrypt(hashed_userid,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM));
        this.userName = SymmetricWithMac.decrypt(this.userName,key,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
    }
    
    void RSA_Encode_User(PublicKey publicKey) throws Exception {
        this.password =  RSA.encrypted(this.password,publicKey);
        this.userName =  RSA.encrypted(this.userName,publicKey);
    }
    void RSA_Decode_User(PrivateKey privateKey) throws Exception {
        this.password = RSA.decrypted(this.password,privateKey);
        this.userName = RSA.decrypted(this.userName,privateKey);    
    }
    
    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", password=" + password + ", userId=" + userId + '}';
    }
    
}
