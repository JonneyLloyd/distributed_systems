package managedbeans;

import entities.UserProfile;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
    private String country;
    private String message;
    private String role;
    
    private boolean updateSuccessful;
    private boolean updateComplete = false;
    
    private String userIdString;

    /**
     * Constructor for UserProfileBean object
     */
    public UserProfileBean() {
    }

    /**
     * userIDString getter
     * 
     * @return userID as a string
     */
    public String getUserIdString() {
        return userIdString;
    }

    /**
     * userIdString setter
     * 
     * @param userIdString
     */
    public void setUserIdString(String userIdString) {
        this.userIdString = userIdString;
    }
    
    /**
     * first name getter
     * @return first name
     */
    public String getF_name() {
        return f_name;
    }

    /**
     * username getter
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Surname getter
     * @return surname
     */
    public String getS_name() {
        return s_name;
    }

    /**
     * Country getter
     * @return country
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * User profile message getter
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * User role getter
     * @return Role
     */
    public String getRole() {
        return role;
    }

    /**
     * Username setter
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *  First name setter
     * @param f_name first name
     */
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    /**
     * Surname setter
     * @param s_name surname
     */
    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    /**
     * Country setter
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * User profile message setter
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * User role setter
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * updateSuccessful getter
     * @return whether update was successful or not
     */
    public boolean isUpdateSuccessful() {
        return updateSuccessful;
    }

    /**
     * updateComplete getter
     * @return whether update is complete
     */
    public boolean isUpdateComplete() {
        return updateComplete;
    }
    
    /**
     * Gets display style attribute depending on whether update is complete or not
     * 
     * @return display style attribute
     */
    public String getInfoDivVisibility() {
        return (this.updateComplete) ? "inherit" : "hidden";
    }
    
    /**
     * Gets display style attribute depending on whether update is successful or not
     * 
     * @return display style attribute
     */
    public String getWarningInfoDivVisibility() {
        return (this.updateSuccessful) ? "hidden" : "inherit";
    }
    
    /**
     * Gets display style attribute for success div depending on whether update is successful or not
     * @return display style attribute
     */
    public String getSuccessInfoDivVisibility() {
        return (!this.updateSuccessful) ? "hidden" : "inherit";
    }
    
    /**
     * Will attempt to update the users profile
     */
    public void onEditProfileSubmitButtonPressed() {
        int userID = loginBean.getUserIDOfLoggedInUser();
        //Edit the profile of the logged in user with new values
        int success = profileEJB.updateUserDetails(userID, this.username, this.f_name, this.s_name, this.message);
        updateComplete = true;
        updateSuccessful = (success == 0);
    } 
    
    /**
     *
     * @return true if userIdString param was sent in the GET request
     */
    public boolean paramsNotSent() {
        //Check if Get Request was passed userIdString
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return (parameterMap.get("userIdString") == null);
    }
    
    /**
     * Will reset beans data with either user id of logged in user or passed param in get request
     */
    public void resetData() {
        //If the userIDString param was not sent in the get request then you are viewing the profile of the logged in user
        //Otherwise you are viewing the profile of the passed userId
        int userId = (paramsNotSent()) ? loginBean.getLoggedInUser().getId() : Integer.parseInt(userIdString);
        reset(userId);
    }
    
    private void reset(int userId) {
        //On landing on the profile page, reset the data to the passed userID
        UserProfile profile = profileEJB.getUserProfileFromID(userId);
        this.f_name = profile.getFName();
        this.s_name = profile.getSName();
        this.message = profile.getMessage();
        this.country = profile.getCountry();
        this.role = profileEJB.getRoleFromId(profile.getId());
        this.username = profileEJB.getUsernameFromId(profile.getId());
    }
}
