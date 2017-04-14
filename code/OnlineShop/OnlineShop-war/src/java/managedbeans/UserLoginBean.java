package managedbeans;

import entities.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import sessionbeans.Login;

/**
 *
 * @author louise
 */
@SessionScoped
@Named(value="userLoginBean")
public class UserLoginBean implements Serializable {
    
    private String email;
    private String password;
    
    private boolean displayWarning;
    
    @EJB
    private Login loginEJB;
    
    /**
     * Constructs user login bean and sets displayWarning to false
     */
    public UserLoginBean() {
        displayWarning = false;
    }

    /**
     * Email getter
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Password getter
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Email setter
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Password setter
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Whether or not to display the warning message
     * 
     * @return true is displayWarning is set to true, otherwise false
     */
    public boolean isDisplayWarning() {
        return displayWarning;
    }
    
    /**
     * Will show or hide warning depending on whether displayWarning is true or false
     * 
     * @return String representing th styling to apply to warning
     */
    public String getDisplayWarningStyle() {
        return (displayWarning) ? "inherit" : "hidden";
    }
    
    /**
     * Will attempt to log in the user
     * If successful will then redirect to index page
     */
    public void submitButtonPressed() {
        //attempt to log user into the system
        int success = loginEJB.loginUser(this.email, this.password);
        if (success == -1) {
            //unsuccessful warning
            displayWarning = true;
        } else {
            //successfull login
            displayWarning = false;
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml");
        }
    }
    
    /**
     * Returns whether a user is logged in
     * 
     * @return true if user is logged in, otherwise false
     */
    public boolean isUserLoggedIn() {
        return loginEJB.isLoggedIn();
    }
    
    /**
     * Returns whether a user is logged out
     * 
     * @return true if user is not logged in, otherwise false
     */
    public boolean isUserLoggedOut() {
        return !loginEJB.isLoggedIn();
    }
    
    /**
     * Gets username of logged in user
     * 
     * @return the username of the current logged in user
     */
    public String getUserNameOfLoggedInUser() {
        return (getLoggedInUser() != null) ? getLoggedInUser().getUsername() : "";
    }
    
    /**
     * checks if current user is admin
     * 
     * @return boolean value of the admin check
     */
    public boolean isAdmin() {
        String role = (getLoggedInUser() != null) ? getLoggedInUser().getRole().getRole() : "";
        System.out.println("TEST " + role + " Equals " + role.equalsIgnoreCase("ADMIN") );
        return role.equalsIgnoreCase("ADMIN");
    }
    
    /**
     * Gets user id of logged in user
     * @return user id of logged in user if user logged in, otherwise -1
     */

    public int getUserIDOfLoggedInUser() {
        return (getLoggedInUser() != null) ? getLoggedInUser().getId() : -1;
    }
    
    /**
     * Gets the User object of user who is logged in
     *
     * @return User object of current logged in user
     */
    public User getLoggedInUser() {
        return (loginEJB.getLoggedInUser() != null) ? loginEJB.getLoggedInUser() : null;
    }
    
    /**
     * Logs user out of system and redirects to loginBox page
     */
    public void logOut(){
        loginEJB.logout();
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "loginBox.xhtml");
    }
    
}
