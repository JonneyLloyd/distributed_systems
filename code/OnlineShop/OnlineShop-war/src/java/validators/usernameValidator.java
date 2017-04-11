package validators;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;
import sessionbeans.ValidateUniqueField;
 
@ManagedBean
@RequestScoped
public class usernameValidator implements Validator {
 
    @EJB
    ValidateUniqueField uniqueFieldEJB;
    
    public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException {
        String username = (String) val;
        
        if (isUsernameAlreadyInDataBase(username)) {
            throwValidatorException("Username already in use");
        }
    }
    
    private boolean isUsernameAlreadyInDataBase(String username) {
        return !uniqueFieldEJB.isUsernameUnique(username);
    }
    
    private void throwValidatorException(String msg) throws ValidatorException {
        FacesMessage message = new FacesMessage();
        message.setSummary(msg);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message); 
    }
    
}