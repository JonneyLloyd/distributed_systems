package messagedrivenbeans;

import POJO.LogMessage;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import sessionbeans.LogUpdater;

/**
 *
 * @author louise
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myQueue") ,
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "myQueue")
})
public class LogMessageBean implements MessageListener {
    
    @EJB
    LogUpdater logUpdater;
    
    public LogMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
           LogMessage logMessage = (LogMessage) message.getBody(Class.forName("POJO.LogMessage"));
           logUpdater.addMessageToLog(logMessage);
        } catch (Exception ex) { 
            ex.printStackTrace();
        }
    }   
}