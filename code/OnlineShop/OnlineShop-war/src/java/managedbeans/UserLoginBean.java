package managedbeans;

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
@Named(value="userLoginBean")
@SessionScoped

public class UserLoginBean implements Serializable {
    
    private String email;
    private String password;
    
    private boolean displayWarning;
    
    @EJB
    private Login loginEJB;

    public UserLoginBean() {
        displayWarning = false;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisplayWarning() {
        return displayWarning;
    }
    
    public String getDisplayWarningStyle() {
        return (displayWarning) ? "inherit" : "hidden";
    }
    
    public void submitButtonPressed() {
        int success = loginEJB.loginUser(this.email, this.password);
        
        if (success == -1) {
            displayWarning = true;
        } else {
            //successfull login
            displayWarning = false;
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml");
        }
    }
    
    public boolean isUserLoggedIn() {
        return loginEJB.isLoggedIn();
    }
    
    public boolean isUserLoggedOut() {
        return !loginEJB.isLoggedIn();
    }
    
    public String getUserNameOfLoggedInUser() {
        if (loginEJB.getLoggedInUser() != null) {
            return loginEJB.getLoggedInUser().getUsername();
        } else {
            return "";
        }
    }
    
    public void logOut(){
        loginEJB.logout();
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "loginBox.xhtml");

    }
    
}
