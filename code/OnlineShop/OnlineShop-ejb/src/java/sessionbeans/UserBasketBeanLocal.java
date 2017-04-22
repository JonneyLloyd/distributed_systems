/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
import entities.StoredBasket;
import entities.User;
import javax.ejb.Local;

/**
 *
 * @author oligavin
 */
@Local
public interface UserBasketBeanLocal {
    
    public void addProduct(User user, Product product);
    
    public void addProduct(User user, Product product, int qty);
    
    public void removeStoredBasket(StoredBasket storedBasket);
    
}
