package managedbeans;

import entities.UserProfile;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sessionbeans.UserSearch;

/**
 *
 * @author louise
 */
@SessionScoped
@Named(value="userSearchBean")
public class UserSearchBean implements Serializable {
    
    @EJB
    UserSearch userSearchEJB;
   
    @Inject
    UserProfileBean profileBean;
   
    String username;
    String f_name;
    String s_name;
    
    List<UserProfile> searchResults;
    
    /**
     *  searchResults getter
     * @return search results list
     */
    public List<UserProfile> getSearchResults() {
        return this.searchResults;
    }
    
    /**
     * Setter for searchResults
     * @param searchResults 
     */
    public void setSearchResults(List<UserProfile> searchResults) {
        this.searchResults = searchResults;
    }

    /**
     * username getter
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * username setter
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * first name getter
     * @return first name
     */
    public String getF_name() {
        return f_name;
    }

    /**
     * first name setter
     * @param f_name
     */
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    /**
     * surname getter
     * @return surname
     */
    public String getS_name() {
        return s_name;
    }

    /**
     * surname setter
     * @param s_name
     */
    public void setS_name(String s_name) {
        this.s_name = s_name;
    }
    
    /**
     * returns the outcome of pressing the search button
     * @return page to navigate to
     */
    public String searchButtonPressed() {
        return "user-viewer";
    }
    
    public void updateSearchResults() {
        this.searchResults = userSearchEJB.searchUsersByNames(this.f_name, this.s_name, this.username);
    }
    
    /**
     * Checks if no users found
     * @return true id no userProfile results found
     */
    public boolean noResultsFound() {
        return userSearchEJB.searchUsersByNames(this.f_name, this.s_name, this.username).isEmpty();
    }
}
