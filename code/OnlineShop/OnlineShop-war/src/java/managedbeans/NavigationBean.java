package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import navigation.NavLink;

/**
 * Controls navigation checking user is authorized
 */
@Named(value = "navigationBean")
@ViewScoped
public class NavigationBean implements Serializable {

    @Inject 
    UserLoginBean loginBean;
    
    private HashMap<String, NavLink> pageLinks;
    private List<NavLink> navLinks;
    
    /**
     * Creates a new instance of NavigationBean
     */
    public NavigationBean() {
        pageLinks = new HashMap<>();
        navLinks = new ArrayList<>();
        
        // Temporary list of pages used to build a HashMap
        List<NavLink> pages = Arrays.asList(
            new NavLink("Home", "index"),
            new NavLink("My Profile", "profile", NavLink.loginState.LOGGED_IN),
            new NavLink("Users", "searchUsers", NavLink.loginState.LOGGED_IN),
            new NavLink("Search Results", "user-viewer", NavLink.loginState.LOGGED_IN),
            new NavLink("Products", "product-viewer"),
            new NavLink("Edit Profile", "editProfile", NavLink.loginState.LOGGED_IN),
            new NavLink("Logout", "logout", NavLink.loginState.LOGGED_IN),
            new NavLink("Login/Regiser", "loginBox", NavLink.loginState.LOGGED_OUT),
            new NavLink("Regiser", "register", NavLink.loginState.LOGGED_OUT),
            new NavLink("Basket", "basket", Arrays.asList("USER")),
            new NavLink("Checkout", "checkout", Arrays.asList("USER")),
            new NavLink("Sales", "sales", Arrays.asList("ADMIN"))
            // Register new pages here
        );
        
        // Build a HashMap of pages using outcome as the key
        pages.forEach((p) -> {
            pageLinks.put(p.getOutcome(), p);
        });
        
        // Temporary list of links to appear in the navbar
        List<String> nav = Arrays.asList(
                "index", "product-viewer", "profile", "searchUsers",
                "logout", "loginBox", "basket", "sales"
                // Add navigation links to pages here
        );
        
        // Build a list of actual NavLinks from the HashMap for the navbar
        nav.stream().map((n) -> pageLinks.get(n)).forEachOrdered((navLink) -> {
            navLinks.add(navLink);
        });
    }
    
    /**
     * Checks if the current user may access a given link
     * @param link  Link to check if user has access to
     */
    private boolean accessPermitted(NavLink link) {
        boolean loggedin = loginBean.isUserLoggedIn();
        
        if (link.getLoginStatePermitted().equals(NavLink.loginState.LOGGED_IN)) {
            if (!loggedin) {
                // User is not logged in
                // Permission denied
                return false;
            }
            String role = loginBean.getLoggedInUser().getRole().getRole();
            if (!link.getRolesPermitted().isEmpty() && !link.getRolesPermitted().contains(role)) {
                // User does not have a matching role
                // Permission denied
                return false;
            }
        }
        else if (link.getLoginStatePermitted() == NavLink.loginState.LOGGED_OUT && loggedin) {
            // User must be logged out
            // Permission denied
            return false;
        }
        // Login was not required
        return true;
    }
    
    /**
     * Checks if the user has access to the requested page.
     * If not, the user is either prompted to login or informed that access is denied.
     */
    public void validateUserAccess() {
        boolean loggedin = loginBean.isUserLoggedIn();
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        
        // Strip off extension of current page
        final Pattern pattern = Pattern.compile("/(.+?).xhtml");
        final Matcher matcher = pattern.matcher(viewId);
        matcher.find();
        String viewOutcome = matcher.group(1);
        
        // Get a link that matches the current page
        NavLink navLink = pageLinks.get(viewOutcome);
        
        if (navLink == null)
            // No defined rules, therefore allow access by default
            return;
        
        if (!accessPermitted(navLink)) {
            // Permission denied
            ConfigurableNavigationHandler navHandler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            
            if (!loggedin)
                navHandler.performNavigation("loginBox");
            else
                navHandler.performNavigation("access-denied");
        }
    }
    
    /**
     * Get only the links the user has permission for
     * @return 
     */
    public List<NavLink> getUserNav() {
        List<NavLink> userNav = navLinks.stream().filter( link -> accessPermitted(link) ).collect(Collectors.toList());
        return userNav;
    }
    
}
