/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface Login {
    
    int loginUser(String email, String password);
    
    void logout();
    
    User getLoggedInUser();
    
    boolean isLoggedIn();
}
