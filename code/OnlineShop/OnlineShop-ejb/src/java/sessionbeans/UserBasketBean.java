/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
import entities.StoredBasket;
import entities.StoredBasketPK;
import entities.User;
import entities.UserBasket;
import entitysessionbeans.StoredBasketFacade;
import entitysessionbeans.UserBasketFacade;
import entitysessionbeans.UserFacade;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oligavin
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
    
//    TODO: interfaces
    
    /**
     * Creates a new instance of UserBasketBean
     */
    public UserBasketBean() {
    }
    
    public void addProduct(User user, Product product) {
        addProduct(user, product, 1);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addProduct(User user, Product product, int qty) {  // Basket or user?
        UserBasket basket = user.getUserBasket();
        if (basket == null) {  // here?
            basket = new UserBasket();
            basket.setUserId(user);
            userBasketFacade.create(basket);
        }
        // TODO: quantity, singleton manager??  merge duplicates?
        StoredBasket storedBasket = storedBasketFacade.find(new StoredBasketPK(basket.getId(), product.getId()));
        if (storedBasket == null) {
            storedBasket = new StoredBasket(basket, product, qty);
            storedBasketFacade.create(storedBasket);
        } else {
            storedBasket.setQty(storedBasket.getQty() + qty);
            storedBasketFacade.edit(storedBasket);
        }
        LOG.log(Level.INFO, "{0} added {1} to their basket.", new Object[]{user.toString(), product.toString()});
    }
    
    public void removeStoredBasket(StoredBasket storedBasket) {
        storedBasketFacade.remove(storedBasket);
    }
    
}
