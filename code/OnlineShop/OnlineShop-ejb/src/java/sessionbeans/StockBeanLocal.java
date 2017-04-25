/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
import javax.ejb.Local;

/**
 *
 * @author jonney
 */
@Local
public interface StockBeanLocal {
    
    /**
     * Decrement quantity of stock from database
     * @param product Product object for product to be decremented
     * @param qty int for amount de decrement the product by
     * @return boolean value for whether operation was successful
     */
    public boolean removeStock(Product product, int qty);
    
}
