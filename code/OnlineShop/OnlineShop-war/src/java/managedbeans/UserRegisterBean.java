package managedbeans;


import sessionbeans.RegisterUser;
import sessionbeans.HashPassword;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author louise
 */
@Named(value="userRegisterBean")
@SessionScoped
public class UserRegisterBean implements Serializable {
    
    @EJB
    private RegisterUser registerEJB;
    
    @EJB
    private HashPassword hashPasswordEJB;
     
    private String email;
    private String password;
    private String username;
    private String f_name;
    private String s_name;
    private String address_line_1;
    private String address_line_2;
    private String country;
    private String postcode;
    private String message;
    
    private boolean registrationSuccessful;
    private boolean registrationComplete = false;
    
    /**
     * Constructor for UserRegisterBean
     */
    public UserRegisterBean() {
    }

    /**
     * Email getter
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Password getter
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Username getter
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * First name getter
     * @return first name
     */
    public String getF_name() {
        return f_name;
    }

    /**
     * Surname getter
     * @return surname
     */
    public String getS_name() {
        return s_name;
    }

    /**
     * address line 1 getter
     * @return address line 1
     */
    public String getAddress_line_1() {
        return address_line_1;
    }

    /**
     * address line 2 getter
     * @return address line 2
     */
    public String getAddress_line_2() {
        return address_line_2;
    }

    /**
     * country getter
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * postcode getter
     * @return postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * profile message getter
     * @return profile message
     */
    public String getMessage() {
        return message;
    }

    /**
     * username setter
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * first name setter
     * @param f_name
     */
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    /**
     * surname setter
     * @param s_name
     */
    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    /**
     * address line 1 setter
     * @param address_line_1
     */
    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    /**
     * address line 2 setter
     * @param address_line_2
     */
    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    /**
     * country setter
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * postcode setter
     * @param postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * profile message setter
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * email setter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * password setter
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * registration successful or not getter
     * @return if registration was successful
     */
    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    /**
     * registration complete or not getter
     * @return if registration is complete
     */
    public boolean isRegistrationComplete() {
        return registrationComplete;
    }
    
    /**
     * Gets display style attribute depending on whether registration is complete or not
     * 
     * @return display style attribute
     */
    public String getInfoDivVisibility() {
        return (this.registrationComplete) ? "inherit" : "hidden";
    }
    
    /**
     * Gets display style attribute depending on whether registration is successful or not
     * 
     * @return display style attribute
     */
    public String getWarningInfoDivVisibility() {
        return (this.registrationSuccessful) ? "hidden" : "inherit";
    }
    
    /**
     * Gets display style attribute for success div depending on whether registration is successful or not
     * 
     * @return display style attribute
     */
    public String getSuccessInfoDivVisibility() {
        return (!this.registrationSuccessful) ? "hidden" : "inherit";
    }
    
    /**
     * Attempts to register user
     * Will display notification of registrations success or failure
     */
    public void onSubmitButtonPressed() {
        String hashedPassword = hashPasswordEJB.hashPassword(this.password);
        
        System.out.println("PASS: " + hashedPassword);
        int success = registerEJB.registerUser(this.email, hashedPassword, this.username, this.f_name, this.s_name, this.address_line_1, this.address_line_2, this.country, this.postcode, this.message);
        registrationComplete = true;
        if (success == -1) {
            registrationSuccessful = false;
        } else {
            registrationSuccessful = true;
            
        }
    }   
}
