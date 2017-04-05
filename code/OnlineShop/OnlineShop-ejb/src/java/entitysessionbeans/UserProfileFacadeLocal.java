/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.UserProfile;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author oligavin
 */
@Local
public interface UserProfileFacadeLocal {

    void create(UserProfile userProfile);

    void edit(UserProfile userProfile);

    void remove(UserProfile userProfile);

    UserProfile find(Object id);

    List<UserProfile> findAll();

    List<UserProfile> findRange(int[] range);

    int count();
    
}
