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
    
    /**
     * Generates a random salt and uses it to hash the password
     * 
     * @param passwordToHash Password entered by user
     * @return Hashed password
     */
    @Override
    public String hashPassword(String passwordToHash) {
        String salt = generateRandomSalt();
        return hashPassword(salt, passwordToHash);
    }
    
    /**
     * Uses a MessageDigest to secure one-way hash functions that take arbitrary-sized data and output a fixed-length hash value
     * Hashing password SHA-512 algorithm
     * Uses a string builder to build the hashed password
     * 
     * @param salt Random string of characters added to the password to hash to make more secure
     * @param passwordToHash The password we want to hash
     * @return the generated password followed by a '~' char, followed by the salt used to hash the password
     */
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
        //Uses UUID to generate a random string of characthers of length 8 to be used as salt for hashing
       return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    
}
