/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.UserProfile;
import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface Profile {
    
    /**
     * Will take in a userID and find that users UserProfile
     *
     * @param userID the id of the user you want the UserProfile of
     * @return a UserProfile instance
     */
    UserProfile getUserProfileFromID(int userID);
    
     /**
     *
     * Will take in a userID and find that users username
     * 
     * @param id user id of user you want username for
     * @return username string of user
     */
    String getUsernameFromId(int id);
    
    /**
     *
     * Will update the user details passed in
     * 
     * @param id user id
     * @param username of user
     * @param f_name firstname of user
     * @param s_name surname of user
     * @param profileMessage user profile message
     * @return
     */
    int updateUserDetails(int id, String username, String f_name, String s_name, String profileMessage);
    
   
}
