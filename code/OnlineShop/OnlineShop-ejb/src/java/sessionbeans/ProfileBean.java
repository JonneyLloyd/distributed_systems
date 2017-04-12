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
import javax.ejb.Stateless;
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
    
    @Override
    public UserProfile getUserProfileFromID(int userID) {
        Query query = em.createNamedQuery("UserProfile.findById");
        query.setParameter("id", userID);
        List<UserProfile> userMatch=  query.getResultList();
        return (userMatch.isEmpty()) ? null : userMatch.get(0);
    }
    
    @Override
    public int updateUserDetails(int id, String username, String f_name, String s_name, String profileMessage) {
        try {
            updateUserProfileDetails(id, f_name, s_name, profileMessage);
            updateUsername(id, username);
        } catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    
    private void updateUserProfileDetails(int id, String f_name, String s_name, String profileMessage) {
        Query query = em.createNamedQuery("UserProfile.findById");
        query.setParameter("id", id);
        List<UserProfile> userPMatch=  query.getResultList();
        UserProfile userP = userPMatch.get(0);
        if (f_name != null && !f_name.equals(""))                   userP.setFName(f_name);
        if (s_name != null && !s_name.equals(""))                   userP.setSName(s_name);
        if (profileMessage != null && !profileMessage.equals(""))   userP.setMessage(profileMessage);
       
        em.persist(userP);
        em.flush();
    }
    
    private void updateUsername(int id, String username) {
        if (!username.equals("")) {
            Query query2 = em.createNamedQuery("User.findById");
            query2.setParameter("id", id);
            List<User> userMatch=  query2.getResultList();
            User user = userMatch.get(0);
            user.setUsername(username);
            em.persist(user);
            em.flush();
        }
    }
}
