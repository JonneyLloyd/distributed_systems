/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author louise
 */
@Stateful
@Remote
public class ValidateUniqueFieldBean implements ValidateUniqueField{
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
     
    /**
     * Will check if email is already in use in the database
     * 
     * @param email
     * @return true is email is not already being used in database
     */
    @Override
    public boolean isEmailUnique(String email) {
        //create named query and set params
        Query query = em.createNamedQuery("User.findByEmail");
        query.setParameter("email", email);
        List<User> userMatch=  query.getResultList();
        //return if result set is empty
        return userMatch.isEmpty();
    }
    
    /**
     * Will check if username is already in use in the database
     * 
     * @param username
     * @return true is username is not already being used in database
     */
    @Override
    public boolean isUsernameUnique(String username) {
        //create named query and set params
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        List<User> userMatch=  query.getResultList();
         //return if result set is empty
        return userMatch.isEmpty();
    }
    
    /**
     * Will check if username is unique excluding the result found for passed userId.
     * 
     * @param username we want to check if unique
     * @param userId loggedIn User ID
     * @return true if username is not a match of a user (excluding logged in user)
     */
    @Override
     public boolean isUsernameUniqueExcludingLoggedInUser(String username, int userId) {
         //create named query and set params
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        List<User> userMatch=  query.getResultList();
        //If no users with that username return false
        //If the only user found with that username is the loggedin user return true
        return (userMatch.isEmpty()) ? true : (userMatch.get(0).getId() == userId);
    }
    
}
