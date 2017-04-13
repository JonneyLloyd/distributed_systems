/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import javax.ejb.Remote;

/**
 *
 * @author louise
 */
@Remote
public interface ValidateUniqueField {
    
    /**
     * Will check if email is already in use in the database
     * 
     * @param email
     * @return true is email is not already being used in database
     */
    boolean isEmailUnique(String email);
    
    /**
     * Will check if username is already in use in the database
     * 
     * @param username
     * @return true is username is not already being used in database
     */
    boolean isUsernameUnique(String username);
    
    /**
     * Will check if username is unique excluding the result found for passed userId.
     * 
     * @param username we want to check if unique
     * @param userId loggedIn User ID
     * @return true if username is not a match of a user (excluding logged in user)
     */
    boolean isUsernameUniqueExcludingLoggedInUser(String username, int userId);
}