package managedbeans;

import entities.UserProfile;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import sessionbeans.Login;
import sessionbeans.Profile;

/**
 *
 * @author louise
 */
@Named(value="userProfileBean")
@SessionScoped
public class UserProfileBean implements Serializable {
    
    @EJB
    private Profile profileEJB;
    
    @Inject 
    UserLoginBean loginBean;
   
    private String username;
    private String f_name;
    private String s_name;
    private String message;
    private String role = "User";
    
    private boolean updateSuccessful;
    private boolean updateComplete = false;
    

    public UserProfileBean() {
    }
    
    public String getF_name() {
        if (f_name == null) {
            init();
        }
        return f_name;
    }

    public String getUsername() {
        return username;
    }

    public String getS_name() {
        return s_name;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isUpdateSuccessful() {
        return updateSuccessful;
    }

    public boolean isUpdateComplete() {
        return updateComplete;
    }
    
    public String getInfoDivVisibility() {
        return (this.updateComplete) ? "inherit" : "hidden";
    }
    
    public String getWarningInfoDivVisibility() {
        return (this.updateSuccessful) ? "hidden" : "inherit";
    }
    
    public String getSuccessInfoDivVisibility() {
        return (!this.updateSuccessful) ? "hidden" : "inherit";
    }
    
    public void onSubmitButtonPressed() {
        int userID = loginBean.getUserIDOfLoggedInUser();
        int success = profileEJB.updateUserDetails(userID, this.username, this.f_name, this.s_name, this.message);
        updateComplete = true;
        if (success == -1) {
            updateSuccessful = false;
        } else {
            updateSuccessful = true;
        }
    } 
    
    private void init() {
        if (loginBean.getLoggedInUser() != null) {
            UserProfile profile = profileEJB.getUserProfileFromID(loginBean.getUserIDOfLoggedInUser());
            this.f_name = profile.getFName();
            this.s_name = profile.getSName();
            this.message = profile.getMessage();
            this.username = loginBean.getUserNameOfLoggedInUser();
        }
    }
}
