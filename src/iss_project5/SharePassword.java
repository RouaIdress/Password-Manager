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
public class SharePassword implements Serializable{
    int id;
    int user_id1;
    int user_id2;
    Password password =new Password();
    int accepted;

    void RSA_Decode(PrivateKey privatekey) throws Exception {
        if(this.password!=null){
            this.password.RSA_Decode_Password(privatekey);
        }
    }
    
    @Override
    public String toString() {
        return "SharePassword{" + "id=" + id + ", user_id1=" + user_id1 + ", user_id2=" + user_id2 + ", password=" + password + ", accepted=" + accepted + '}';
    }
    
}
