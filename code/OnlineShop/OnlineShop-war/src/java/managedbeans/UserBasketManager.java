package managedbeans;

import entities.Product;
import entities.StoredBasket;
import entities.User;
import entities.UserBasket;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import sessionbeans.UserBasketBean;

/**
 * Managed bean for handling operations on a user's basket
 */
@Named(value = "userBasketManager")
@RequestScoped
public class UserBasketManager implements Serializable {

    @EJB
    UserBasketBean userBasketBean;

    @Inject 
    UserLoginBean loginBean;

    /**
     * Creates a new instance of UserBasketManager
     */
    public UserBasketManager() {
    }
    
    /**
     * Gets all the entries (products) in a user's basket
     * @return list of entries
     */
    public List<StoredBasket> getAllProducts() {
        UserBasket userBasket = loginBean.getLoggedInUser().getUserBasket();
        if (userBasket != null)
            return (List<StoredBasket>) userBasket.getStoredBasketCollection();
        else
            return new ArrayList<>();
    }
    
    /**
     * Add a product with a given quantity to the current user's basket
     * @param product   The product to add
     * @param qty       The quantity of the product
     */
    public void addProduct(Product product, int qty) {
        User user = loginBean.getLoggedInUser();
        userBasketBean.addProduct(user, product, qty);
    }
    
    /**
     * Remove an entry in the users basket
     * @param storedBasket  The entry (mapping user's basket and product)
     */
    public void removeStoredBasket(StoredBasket storedBasket) {
        userBasketBean.removeStoredBasket(storedBasket);
    }
    
    public String checkoutButtonPressed(){
        return "checkout";
    }
    
}
