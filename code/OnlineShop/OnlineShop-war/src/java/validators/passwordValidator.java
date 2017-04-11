package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author louise
 */
@FacesValidator("passwordvalidator")
public class passwordValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException {
        String password = (String) val;
        
        if (!passwordContainsOneDigit(password)) {
            throwValidatorException("Password must contain at least one digit");
        }
        
        if (!passwordContainsOneUppercase(password)) {
            throwValidatorException("Password must contain at least one upper case letter");
        }
        
        if (!passwordContainsOneSpecialCaseChar(password)) {
            throwValidatorException("Password must contain at least one special case character (?=.*[@#$%^&+=])");
        }
         
        if (passwordContainsWhitespaces(password)) {
            throwValidatorException("Password must not contain whitespaces");
        }

    }
    
    private boolean passwordContainsOneDigit(String password) {
        String regExp = "^(?=.*\\d).+$";
        return password.matches(regExp);
    }
    
    private boolean passwordContainsOneUppercase(String password) {
        String regExp = "^(?=.*[A-Z]).+$";
        return password.matches(regExp);
    }
    
    private boolean passwordContainsOneSpecialCaseChar(String password) {
        String regExp = "^(?=.*[@#$%^&+=]).+$";
        return password.matches(regExp);
    }
    
    private boolean passwordContainsWhitespaces(String password) {
        String regExp = "^(?=\\S+$)$.+";
        return password.matches(regExp);
    }
    
    private void throwValidatorException(String msg) throws ValidatorException {
        FacesMessage message = new FacesMessage();
        message.setSummary(msg);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message); 
    }
    
}
