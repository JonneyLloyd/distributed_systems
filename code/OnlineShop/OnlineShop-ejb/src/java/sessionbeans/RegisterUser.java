package sessionbeans;

import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface RegisterUser {
    
    public int registerUser(String email, String password, String username, String f_name, String s_name, String address_line_1, String address_line_2, String country, String postcode, String message);
   
}
