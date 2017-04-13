package sessionbeans;

import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface RegisterUser {
    
    /**
     *
     * Will register a new user with the given parameters
     * 
     * @param email users email address
     * @param password users password to log in
     * @param username users unique username
     * @param f_name users firstname
     * @param s_name users surname
     * @param address_line_1 users address part 1
     * @param address_line_2 users address part 2
     * @param country users country
     * @param postcode users postcode
     * @param message users profile message
     * @return
     */
    public int registerUser(String email, String password, String username, String f_name, String s_name, String address_line_1, String address_line_2, String country, String postcode, String message);
   
}
