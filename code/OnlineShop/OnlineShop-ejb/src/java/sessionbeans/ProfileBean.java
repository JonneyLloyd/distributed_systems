/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import entities.UserProfile;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author louise
 */
@Stateful
@LocalBean

public class ProfileBean implements Profile{
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
    @Inject
    LoginBean loginEJB;        
    
    /**
     * Will take in a userID and find that users UserProfile
     *
     * @param userID the id of the user you want the UserProfile of
     * @return a UserProfile instance
     */
    @Override
    public UserProfile getUserProfileFromID(int userID) {
        // create named query and set parameter
        Query query = em.createNamedQuery("UserProfile.findById");
        query.setParameter("id", userID);
        List<UserProfile> userMatch=  query.getResultList();
        // return either matching user or null if no user found
        return (userMatch.isEmpty()) ? null : userMatch.get(0);
    }
    
    /**
     *
     * Will update the user details passed in
     * 
     * @param id user id
     * @param username of user
     * @param f_name first name of user
     * @param s_name surname of user
     * @param profileMessage user profile message
     * @return
     */
    @Override
    public int updateUserDetails(int id, String username, String f_name, String s_name, String profileMessage) {
        //update the users UserProfile and User tables in db with new values
        updateUserProfileDetails(id, f_name, s_name, profileMessage);
        updateUsername(id, username);
        return 0;
    }
    
    /**
     *
     * Will take in a userID and find that users username
     * 
     * @param id user id of user you want username for
     * @return username string of user
     */
    @Override
    public String getUsernameFromId(int id) {
        // create named query and set parameter
        Query query = em.createNamedQuery("User.findById");
        query.setParameter("id", id);
        List<User> userMatch=  query.getResultList();
        //return first result in result set
        return userMatch.get(0).getUsername();
    }
    
    private void updateUserProfileDetails(int id, String f_name, String s_name, String profileMessage) {
        // create named query and set parameter
        Query query = em.createNamedQuery("UserProfile.findById");
        query.setParameter("id", id);
        List<UserProfile> userPMatch=  query.getResultList();
        // get userProfile with matching id
        UserProfile userP = userPMatch.get(0);
        //if parameters were set, update values in userProfile
        if (f_name != null && !f_name.equals(""))                   userP.setFName(f_name);
        if (s_name != null && !s_name.equals(""))                   userP.setSName(s_name);
        if (profileMessage != null && !profileMessage.equals(""))   userP.setMessage(profileMessage);
       
         //update database using entity manager
        em.persist(userP);
        em.flush();
    }
    
    private void updateUsername(int id, String username) {
        if (!username.equals("")) {
            // create named query and set parameter
            Query query2 = em.createNamedQuery("User.findById");
            query2.setParameter("id", id);
            // Find user with matching id
            List<User> userMatch=  query2.getResultList();
            User user = userMatch.get(0);
            //set that users username
            user.setUsername(username);
            
            //update database using entity manager
            em.persist(user);
            em.flush();
        }
    }
}
