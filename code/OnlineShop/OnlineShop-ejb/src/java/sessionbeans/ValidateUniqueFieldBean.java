/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import java.util.List;
import javax.ejb.LocalBean;
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
     
    public boolean isEmailUnique(String email) {
        Query query = em.createNamedQuery("User.findByEmail");
        query.setParameter("email", email);
        List<User> userMatch=  query.getResultList();
        return userMatch.isEmpty();
    }
    
    public boolean isUsernameUnique(String username) {
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        List<User> userMatch=  query.getResultList();
        return userMatch.isEmpty();
    }
    
}
