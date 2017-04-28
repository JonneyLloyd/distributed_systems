
package managedbeans;

import POJO.LogMessage;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 *
 * @author louise
 */
@RequestScoped
@Named(value="messageLogger")
public class MessageLogger implements Serializable {

    @Resource(mappedName = "jms/myQueue")
    private Queue myQueue;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    @Inject
    UserLoginBean loginBean;
    
    /**
     * Will create a LogMessage object
     * and send message to MyQueue
     * @param message to be set as msg string in LogMessage
     */
    public void sendMessageToLog(String message) {
        LogMessage msg = new LogMessage();
        msg.setMessage(message);
        msg.setUser(loginBean.getLoggedInUser());
        msg.setDate(new Date());
        context.createProducer().send(myQueue, msg);
    }
}
