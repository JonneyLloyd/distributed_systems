/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import entities.UserProfile;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author louise
 */
@Stateless
@LocalBean
public class UserSearchBean implements UserSearch{

    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
    /**
     *Will search for users with matching first name, surname and username
     * 
     * @param f_name users first name
     * @param s_name users surname
     * @param username username
     * @return
     */
    @Override
    public List<UserProfile> searchUsersByNames(String f_name, String s_name, String username) {
       //Check which attributes are not set and search accordingly
       if (notSet(f_name) && notSet(s_name) && notSet(username))    return getAllUserProfiles();
       if (notSet(f_name))                                          return searchUsersBySurnameAndUsername(s_name, username);
       if (notSet(s_name))                                          return searchUsersByFirstnameAndUsername(f_name, username);
       if (notSet(username))                                        return searchUsersByFirstnameAndSurname(f_name, s_name);
       else                                                         return searchUsersByFirstnameSurnameUsername(f_name, s_name, username);
    }
    
    /**
     * Gets a list of all userProfiles in the database
     * @return list of UserProfile objects
     */
    public List<UserProfile> getAllUserProfiles() {
        // create named query and set parameter
        Query query = em.createNamedQuery("UserProfile.findAll");
        // return query result
        return query.getResultList();
    }
     
    /**
     * Searches for userProfiles with matching surname & username
     * @param s_name surname
     * @param username
     * @return list of UserProfiles
     */
    private List<UserProfile> searchUsersBySurnameAndUsername(String s_name, String username) {
        //Search for users with matching surnames and usernames
        List<UserProfile> snameUsers = searchUsersBySurname(s_name);
        List<UserProfile> usernameUsers = getUserProfilesFromUsers(searchUsersByUsername(username));
        if (notSet(username))                               return snameUsers;
        else if (notSet(s_name) || snameUsers.isEmpty())    return usernameUsers;
        //if both attributes are set, search for users that have both a matching surname in their UserProfile and username in User
        else                                                return getUserProfilesWithMatchingUsername(snameUsers, username);
    }
    
    /**
     * Searches for userProfiles with matching first name & username
     * @param f_name first name
     * @Param username username
     * @return list of UserProfiles
     */
    private List<UserProfile> searchUsersByFirstnameAndUsername(String f_name, String username) {
        //Search for users with matching firstnames and username
        List<UserProfile> fnameUsers = searchUsersByFirstName(f_name);
        List<UserProfile> usernameUsers = getUserProfilesFromUsers(searchUsersByUsername(username));
        if (notSet(username))                             return fnameUsers;
        else if (notSet(f_name) || fnameUsers.isEmpty())  return usernameUsers;
        //if both attributes are set, search for users that have both a matching firstname in their UserProifle and username in User
        else                                              return getUserProfilesWithMatchingUsername(fnameUsers, username);
    }
    
    /**
     * Searches for userProfiles with matching surname & first name
     * @param f_name first name
     * @param s_name surname
     * @return list of UserProfiles
     */
    private List<UserProfile> searchUsersByFirstnameAndSurname(String f_name, String s_name) {
        // if firstname or surname is not set return the search results of the other attributes search
        if (notSet(s_name))         return searchUsersByFirstName(f_name);
        else if (notSet(f_name))    return searchUsersBySurname(s_name);
        else  {
            //If both firstname and surname are set, query the UserProfiles for a profile with a match on both attributes
            Query query = em.createNamedQuery("UserProfile.findByFNameAndSName");
            query.setParameter("sName", s_name);
            query.setParameter("fName", f_name);
            return query.getResultList();
        }
    }
    
    /**
     * Searches for userProfiles with matching surname, first name, & username
     * @param f_name first name
     * @param s_name surname
     * @param username
     * @return list of UserProfiles
     */
    private List<UserProfile> searchUsersByFirstnameSurnameUsername(String f_name, String s_name, String username) {
        //Get the results of the userProfiles of users with a matching firstname and surname and then search for users with a matching username in users
        return getUserProfilesWithMatchingUsername(searchUsersByFirstnameAndSurname(s_name, f_name), username);
    }
    
    /**
     * Will search database for the user with the matching specified username
     * 
     * @param username of user you want to find
     * @return list of User objects
     */
    public List<User> searchUsersByUsername(String username) {
        // create named query and set parameter
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        // return query result
        return query.getResultList();
    }
    
    /**
     * Will search database for the users with the matching specified first name
     * 
     * @param f_name first name you are searching for
     * @return list of User objects
     */
    public List<UserProfile> searchUsersByFirstName(String f_name) {
        // create named query and set parameter
        Query query = em.createNamedQuery("UserProfile.findByFName");
        query.setParameter("fName", f_name);
        // return query result
        return query.getResultList();
    }
    
    /**
     * Will search database for the users with the matching specified surname
     * 
     * @param s_name surname you are searching for
     * @return list of User objects
     */
    public List<UserProfile> searchUsersBySurname(String s_name) {
        // create named query and set parameter
        Query query = em.createNamedQuery("UserProfile.findBySName");
        query.setParameter("sName", s_name);
        // return query result
        return query.getResultList();
    }
    
    /**
     * Checks for matches in the userProfie list with matching username
     * @param list of userProfiles
     * @param username to match
     * @return userProfile
     */
    private List<UserProfile> getUserProfilesWithMatchingUsername(List<UserProfile> list, String username) {
        List<User> users = new ArrayList<>();
        //For each UserProfile in the list
        list.forEach((user) -> {
            Query query = em.createNamedQuery("User.findById");
            // take the uderID and search for the User with that id.
            query.setParameter("id", user.getId());
            User u = (User) query.getResultList().get(0);
            //If that user has a matching username add it to the results list
            if (u.getUsername().equals(username)) {
                users.add(u);
            }
        });
        return getUserProfilesFromUsers(users);
    }
    
    /**
     * Check if string parameter is not set
     * @param param
     * @return true if unset
     */
    private boolean notSet(String param) {
        //Check if parameter is set
        return (param == null || param.equals(""));
    }
    
    /**
     * Takes in a list of users and gets those users USerProfiles
     * @param userProfilesUsername List of users
     * @return userProfile list
     */
    private List<UserProfile> getUserProfilesFromUsers(List<User> userProfilesUsername) {
        //Go through the list of Users
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfilesUsername.forEach((user) -> {
            //Using the ID, query the UserProfiles
            int id = user.getId();
            Query query = em.createNamedQuery("UserProfile.findById");
            query.setParameter("id", id);
            //Add to the results the USerProfiles with matching userID
            userProfiles.add((UserProfile) query.getResultList().get(0));
        });
        return userProfiles;
    }
}
