package sessionbeans;

import entities.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author louise
 */
@Stateful
@LocalBean
public class LoginBean implements Login {
    
    private boolean loggedIn = false;
    private int loggedInUserId = -1;
    
    /**
     * Bean which has business login for logging in and out a user
     */
    public LoginBean() {
    }
   
    @EJB
    private HashPassword hashPasswordEJB;
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
    @Override
    public int loginUser(String username, String password) {
        
        //Query for user with matching username
        Query q1 = em.createNamedQuery("User.findByUsername");
        q1.setParameter("username", username);
        List<User> userMatch=  q1.getResultList();
        if (userMatch.isEmpty()) {
            //no user found with specified username
            return -1;
        } else {
            //user found with specified username - Check if password is a match
            
            User match = userMatch.get(0);
            String pass = match.getPass();
            
            //passwords are stored in DB in format hashedpassword~salt
            //Salt is the random string used to hash the passwords
            String salt = getSaltFromDBPassword(pass);
            String hashedPass = getHashedPasswordFromDBPassword(pass);
            
            //hash the entered password with the same salt that was used for the user with the matching username
            String hashedEntry = getHashedPasswordFromDBPassword(hashPasswordEJB.hashPassword(salt, password));
            
            if (hashedEntry.equals(hashedPass)){
                setLoggedIn();
                this.loggedInUserId = match.getId();
            } else {
                return -1;
            }
        }
       return 0;
    }
    
    @Override
    public User getLoggedInUser() {
        if (this.loggedInUserId == -1) {
            return null;
        } else {
            Query q1 = em.createNamedQuery("User.findById");
            q1.setParameter("id", this.loggedInUserId);
            List<User> userMatch = q1.getResultList();
            return userMatch.get(0);
        }
    }
    
    @Override
    public boolean isLoggedIn() {
        return this.loggedIn;
    }
    
    @Override
    public void logout() {
        setLoggedOut();
        this.loggedInUserId = -1;
    }
    
    private void setLoggedIn() {
        this.loggedIn = true;
    }
    
    private void setLoggedOut() {
        this.loggedIn = false;
    }
    
    private String getSaltFromDBPassword(String dbPass) {
        //splits the hashed password from the database to return the salt used to hash that password
        return dbPass.split("~")[1];
    }
    
     private String getHashedPasswordFromDBPassword(String dbPass) {
         //splits the hashed password from the database to return the original password
        return dbPass.split("~")[0];
    }
}
