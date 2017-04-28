package validators;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;
import javax.inject.Inject;
import managedbeans.UserLoginBean;
import sessionbeans.ValidateUniqueField;
 
/**
 *
 * @author louise
 */
@ManagedBean
@RequestScoped
public class usernameValidator implements Validator {
 
    @EJB
    ValidateUniqueField uniqueFieldEJB;
    
    @Inject
    UserLoginBean loginBean;
    
    /**
     * Will validate the username entered is not already used in the database
     * 
     * @param context FacesContext
     * @param c UIComponent you are validating on
     * @param val Object you wish to validate
     * @throws ValidatorException
     */
    public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException {
        String username = (String) val;
        
        if (isUsernameAlreadyInDataBase(username)) {
            throwValidatorException("Username already in use");
        }
    }
    
    /**
     * Checks if passed username string is already in use in the database
     * @param username
     * @return true if username already in db
     */
    private boolean isUsernameAlreadyInDataBase(String username) {
        return (loginBean.isUserLoggedIn()) ? !uniqueFieldEJB.isUsernameUniqueExcludingLoggedInUser(username, loginBean.getUserIDOfLoggedInUser()) : !uniqueFieldEJB.isUsernameUnique(username);
    }
    
    /**
     * Throws a validator exception with the specified message
     * @param msg to be thrown in exception
     * @throws ValidatorException 
     */
    private void throwValidatorException(String msg) throws ValidatorException {
        // Creates a FacesMessage and sets message specifics
        FacesMessage message = new FacesMessage();
        message.setSummary(msg);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //throws Validator exception 
        throw new ValidatorException(message); 
    }
    
}