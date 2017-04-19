/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Role;
import entities.User;
import entities.UserProfile;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author louise
 */
@TransactionManagement(TransactionManagementType.CONTAINER)

@Stateless
@LocalBean
public class RegisterUserBean implements RegisterUser {
    
    private Role role;
    private User user;
    private UserProfile profile;
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
   
    @Override
    public int registerUser(String email, String password, String username, String f_name, String s_name, String address_line_1, String address_line_2, String country, String postcode, String message) {
        try {
         registerUserDetails(email, password, username);
         registerUserProfile(f_name, s_name, address_line_1, address_line_2, country, postcode, message);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
      
        return 0;
    }
    
    /**
     * Register the users details in the User table
     * 
     * @param email
     * @param password
     * @param username
     */
    public void registerUserDetails(String email, String password, String username) {
        role = getRoleFromDB(); 
        user = new User();
        user.setEmail(email);
        user.setPass(password);
        user.setActive(true);
        user.setUsername(username);
        user.setRole(role);
        //update database
        em.persist(user);
        em.flush();
    }
    
    private Role getRoleFromDB() {
        // create named query and set params
        Query query = em.createNamedQuery("Role.findByRole");
        query.setParameter("role", "USER");
        List<Role> roleMatch=  query.getResultList();
        // Return first result
        return roleMatch.get(0);
    }
    
    /**
     *Register the users UserProfile
     * 
     * @param f_name
     * @param s_name
     * @param address_line_1
     * @param address_line_2
     * @param country
     * @param postcode
     * @param message
     */
    public void registerUserProfile(String f_name, String s_name, String address_line_1, String address_line_2, String country, String postcode, String message) {
        profile = new UserProfile();
        profile.setId(user.getId());
        profile.setFName(f_name);
        profile.setSName(s_name);
        profile.setAddressLine1(address_line_1);
        profile.setAddressLine2(address_line_2);
        profile.setCountry(country);
        profile.setPostcode(postcode);
        profile.setMessage(message);
        
        //update database
        em.persist(profile);
        em.flush();
    }
}