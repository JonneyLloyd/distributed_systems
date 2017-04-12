/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a navigation link and it's authentication requirements
 */
public class NavLink {
    
    public enum loginState{LOGGED_IN, LOGGED_OUT, ANY};
    
    private String name;
    private String outcome;
    private List<String> rolesPermitted;
    private loginState loginStatePermitted;

    /**
     *
     * @param name      Visible human readable name for the link
     * @param outcome   Outcome for navigation rules
     */
    public NavLink(String name, String outcome) {
        this.name = name;
        this.outcome = outcome;
        this.rolesPermitted = new ArrayList<>();
        this.loginStatePermitted = loginState.ANY;
    }

    /**
     *
     * @param name      Visible human readable name for the link
     * @param outcome   Outcome for navigation rules
     * @param loginStatePermitted  Permitted login state which may access the link
     */
    public NavLink(String name, String outcome, loginState loginStatePermitted) {
        this.name = name;
        this.outcome = outcome;
        this.loginStatePermitted = loginStatePermitted;
        this.rolesPermitted = new ArrayList<>();
    }

    /**
     *
     * @param name      Visible human readable name for the link
     * @param outcome   Outcome for navigation rules
     * @param rolesPermitted        List of permitted roles which may access the link
     */
    public NavLink(String name, String outcome, List<String> rolesPermitted) {
        this.name = name;
        this.outcome = outcome;
        this.rolesPermitted = rolesPermitted;
        this.loginStatePermitted = loginState.LOGGED_IN;
    }

    /**
     *
     * @return  Visible human readable name for the link
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return  Outcome for navigation rules
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     *
     * @return  List of permitted roles which may access the link
     */
    public List<String> getRolesPermitted() {
        return rolesPermitted;
    }

    /**
     *
     * @return  Permitted login state which may access the link
     */
    public loginState getLoginStatePermitted() {
        return loginStatePermitted;
    }
    
}
