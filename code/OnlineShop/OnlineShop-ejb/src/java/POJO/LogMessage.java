package POJO;

import entities.User;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author louise
 */
public class LogMessage implements Serializable {
    
    User user;
    String message;
    Date date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
