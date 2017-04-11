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
    

    public UserRegisterBean() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getF_name() {
        return f_name;
    }

    public String getS_name() {
        return s_name;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public String getCountry() {
        return country;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getMessage() {
        return message;
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

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    public boolean isRegistrationComplete() {
        return registrationComplete;
    }
    
    public String getInfoDivVisibility() {
        return (this.registrationComplete) ? "inherit" : "hidden";
    }
    
    public String getWarningInfoDivVisibility() {
        return (this.registrationSuccessful) ? "hidden" : "inherit";
    }
    
    public String getSuccessInfoDivVisibility() {
        return (!this.registrationSuccessful) ? "hidden" : "inherit";
    }
    
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
