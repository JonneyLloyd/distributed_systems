package validators;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;
import sessionbeans.ValidateUniqueField;
 
/**
 *
 * @author louise
 */
@ManagedBean
@RequestScoped
public class emailValidator implements Validator {
    
    @EJB
    ValidateUniqueField uniqueEJB;
 
    /**
     * Will validate the email entered matches an email format and is not already used in the database
     * 
     * @param context FacesContext
     * @param c UIComponent you are validating on
     * @param val Object you wish to validate
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException {
        String email = (String) val;
        
        if (!matchesEmailRegex(email)) {
            throwValidatorException("Invalid email format");
        }
        
        if (alreadyInDatabase(email)) {
            throwValidatorException("That email address is already in use in our database");
        } 
    }
    
    /**
     * Checks if passed email string matches the regular expression for an email address
     * @param email
     * @return true if matches, otherwise false
     */
    private boolean matchesEmailRegex(String email) {
        String regExp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(regExp);
    }
    
    /**
     * Checks if passed email string is already in the database
     * @param email
     * @return true if in database already
     */
    private boolean alreadyInDatabase(String email) {
        //Uses uniqueField  EJB to check if email is unique
        return !uniqueEJB.isEmailUnique(email);
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