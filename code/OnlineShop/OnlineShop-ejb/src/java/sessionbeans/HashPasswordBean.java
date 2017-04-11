/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.security.MessageDigest;
 import java.util.UUID;

/**
 *
 * @author louise
 */
@Stateless
@LocalBean
public class HashPasswordBean implements HashPassword{
    
    @Override
    public String hashPassword(String passwordToHash) {
        String salt = generateRandomSalt();
        return hashPassword(salt, passwordToHash);
    }
    
    @Override
    public String hashPassword(String salt, String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length ;i++){
               sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
       }
        return generatedPassword + "~" + salt;
    }
    
    private String generateRandomSalt() {
       return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    
}
