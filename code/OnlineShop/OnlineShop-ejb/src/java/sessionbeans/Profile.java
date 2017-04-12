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
    
    UserProfile getUserProfileFromID(int userID);
    
    int updateUserDetails(int id, String username, String f_name, String s_name, String profileMessage);
}
