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
    private User loggedInUser;
    
    /**
     * Bean which has business login for logging in and out a user
     */
    public LoginBean() {
    }
   
    @EJB
    private HashPassword hashPasswordEJB;
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
     /**
     * Will log in the user with the given username and password
     * 
     * @param username of user attempting login
     * @param password of user attempting login
     * @return 0 for success, -1 for failure
     */
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
                this.loggedInUser = match;
            } else {
                return -1;
            }
        }
       return 0;
    }
    
    /**
     * Will return a User object of the user who is currently logged in
     * 
     * @return logged in user
     */
    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }
    
    /**
     * Checks whether or not there is currently a user logged in
     * 
     * @return true if there is currently a user logged in, false otherwise
     */
    @Override
    public boolean isLoggedIn() {
        return this.loggedIn;
    }
    
    /**
     * Will log out the current logged in user
     */
    @Override
    public void logout() {
        setLoggedOut();
        this.loggedInUser = null;
    }
    
    /**
     * Set loggedIn to true
     */
    private void setLoggedIn() {
        this.loggedIn = true;
    }
    
    /**
     * Set loggedIn to false
     */
    private void setLoggedOut() {
        this.loggedIn = false;
    }
    
    /**
     * Splits the hashed password from the database to return the salt used to hash that password
     * @param dbPass  password from database
     * @return salt used to hash password
     */
    private String getSaltFromDBPassword(String dbPass) {
        return dbPass.split("~")[1];
    }
    
    /**
     * Splits the hashed password from the database to return the original password
     * @param dbPass password from database
     * @return the hashed password not including the salt
     */
     private String getHashedPasswordFromDBPassword(String dbPass) {
        return dbPass.split("~")[0];
    }

     
    /**
     * refreshes logged in user
     */
    @Override
    public void refresh() {
        if (this.loggedInUser == null)
            return;
        
        Query q1 = em.createNamedQuery("User.findById");
        q1.setParameter("id", this.loggedInUser.getId());
        q1.setHint("eclipselink.refresh", "true");
        List<User> userMatch = q1.getResultList();
        this.loggedInUser = userMatch.get(0);
    }
}
