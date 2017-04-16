/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import javax.ejb.Local;
import javax.ejb.Stateful;

/**
 *
 * @author louise
 */
@Local
public interface Login {
    
    /**
     * Will log in the user with the given username and password
     * 
     * @param username of user attempting login
     * @param password of user attempting login
     * @return 0 for success, -1 for failure
     */
    int loginUser(String username, String password);
    
    /**
     * Will log out the current logged in user
     */
    void logout();
    
    /**
     * Will return a User object of the user who is currently logged in
     * 
     * @return logged in user
     */
    User getLoggedInUser();
    
    /**
     * Checks whether or not there is currently a user logged in
     * 
     * @return true if there is currently a user logged in, false otherwise
     */
    boolean isLoggedIn();
}
