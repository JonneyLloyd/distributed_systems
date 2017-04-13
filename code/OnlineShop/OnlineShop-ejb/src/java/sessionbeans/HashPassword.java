/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface HashPassword {
    
     /**
     * Generates a random salt and uses it to hash the password
     * 
     * @param password Password entered by user
     * @return Hashed password + '~' + salt used to hash password
     */
    String hashPassword(String password);
    
    /**
     *Using provided salt will hash password and return the hashed password with the salt appended on at the end
     * 
     * @param salt random string of characters to be added to password to make more secure in hash
     * @param password password you want to hash
     * @return Hashed password + '~' + salt used to hash password
     */
    String hashPassword(String salt, String password);
    
}
