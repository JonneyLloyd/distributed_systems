/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Product;
import entities.StoredBasket;
import entities.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import sessionbeans.UserBasketBean;

/**
 *
 * @author oligavin
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
    
    public List<StoredBasket> getAllProducts() {
//        return this.storedBasketList;
        return (List<StoredBasket>) loginBean.getLoggedInUser().getUserBasket().getStoredBasketCollection();
    }
    
    public void addProduct(Product product, int qty) {
        System.out.println(">>>>>>>>>>>>Adding with qty: "+qty);
        User user = loginBean.getLoggedInUser();
        userBasketBean.addProduct(user, product, qty);
    }
    
    public void removeStoredBasket(StoredBasket storedBasket) {
        userBasketBean.removeStoredBasket(storedBasket);
    }
    
}
