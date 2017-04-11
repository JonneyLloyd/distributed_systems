
package managedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named(value = "userProfileInfoBean")
@SessionScoped
@ManagedBean(name="userProfileInfoBean")
public class UserProfileInfoBean {

    private String name;
    private String username;
    private String profileMessage;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileMessage() {
        return profileMessage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfileMessage(String profileMessage) {
        this.profileMessage = profileMessage;
    }
    
    
}
