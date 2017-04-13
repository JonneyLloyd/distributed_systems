
package sessionbeans;

import entities.UserProfile;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface UserSearch {
    
    /**
     *Will search for users with matching first name, surname and username
     * 
     * @param f_name users first name
     * @param s_name users surname
     * @param username username
     * @return
     */
    List<UserProfile> searchUsersByNames(String f_name, String s_name, String username);
}
