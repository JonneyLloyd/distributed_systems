package sessionbeans;

import entities.Product;
import entities.StoredBasket;
import entities.StoredBasketPK;
import entities.User;
import entities.UserBasket;
import entitysessionbeans.StoredBasketFacade;
import entitysessionbeans.UserBasketFacade;
import entitysessionbeans.UserFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Handles operations on a user's basket using entities and facades
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless
@LocalBean
public class UserBasketBean implements UserBasketBeanLocal {

    @EJB
    private UserFacade userFacade;

    @EJB
    private UserBasketFacade userBasketFacade;
    
    @EJB
    private StoredBasketFacade storedBasketFacade;
    
    private static final Logger LOG = Logger.getLogger(UserBasketBean.class.getName());
    
    /**
     * Creates a new instance of UserBasketBean
     */
    public UserBasketBean() {
    }
    
    /**
     * Add a product to a given user's basket
     * @param user      The user for which a product will be added to their basket
     * @param product   The product to be added
     */
    @Override
    public void addProduct(User user, Product product) {
        addProduct(user, product, 1);
    }
    
    /**
     * Add a product to a given user's basket and the quantity
     * @param user      The user for which a product will be added to their basket
     * @param product   The product to be added
     * @param qty       The quantity of the product
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void addProduct(User user, Product product, int qty) {
        UserBasket basket = user.getUserBasket();
        if (basket == null) {  // Create a basket for the user if it doesn't alrady exist
            basket = new UserBasket();
            basket.setUserId(user);
            userBasketFacade.create(basket);
            userBasketFacade.flush();
        }
        // Find the stored basket (i.e. product in a user basket) if it exists
        StoredBasket storedBasket = storedBasketFacade.find(new StoredBasketPK(basket.getId(), product.getId()));
        if (storedBasket == null) {
            // Create an entry and add the product
            storedBasket = new StoredBasket(basket, product, qty);
            storedBasketFacade.create(storedBasket);
        } else {
            // Modify the entry (adding more quantity) if it already existed
            storedBasket.setQty(storedBasket.getQty() + qty);
            storedBasketFacade.edit(storedBasket);
        }
        LOG.log(Level.INFO, "{0} added {1} to their basket.", new Object[]{user.toString(), product.toString()});
    }
    
    /**
     * Remove a stored basket (i.e. an item from the user's basket)
     * @param storedBasket  The entry for a product in a user's basket
     */
    @Override
    public void removeStoredBasket(StoredBasket storedBasket) {
        storedBasketFacade.remove(storedBasket);
    }
    
}
