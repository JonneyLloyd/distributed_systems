package sessionbeans;

import entities.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sessionbeans.HashPassword;

/**
 *
 * @author louise
 */
@Stateful
@LocalBean
public class LoginBean implements Login {
    
    private boolean loggedIn = false;
    private User loggedInUser = null;
    
    public LoginBean() {
    }
   
    @EJB
    private HashPassword hashPasswordEJB;
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
    @Override
    public int loginUser(String email, String password) {
        
        //Query for user with matching email
        Query q1 = em.createNamedQuery("User.findByEmail");
        q1.setParameter("email", email);
        List<User> userMatch=  q1.getResultList();
        if (userMatch.isEmpty()) {
            //no user found with specified email
            return -1;
        } else {
            //user found with specified email - Check if password is a match
            
            User match = userMatch.get(0);
            String pass = match.getPass();
            
            //passwords are stored in DB in format hashedpassword~salt
            //Salt is the random string used to hash the passwords
            String salt = getSaltFromDBPassword(pass);
            String hashedPass = getHashedPasswordFromDBPassword(pass);
            
            //hash the entered password with the same salt that was used for the user with the matching email
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
    
    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }
    
    @Override
    public boolean isLoggedIn() {
        return this.loggedIn;
    }
    
    @Override
    public void logout() {
        setLoggedOut();
        this.loggedInUser = null;
    }
    
    private void setLoggedIn() {
        this.loggedIn = true;
    }
    
    private void setLoggedOut() {
        this.loggedIn = false;
    }
    
    private String getSaltFromDBPassword(String dbPass) {
        return dbPass.split("~")[1];
    }
    
     private String getHashedPasswordFromDBPassword(String dbPass) {
        return dbPass.split("~")[0];
    }
}
