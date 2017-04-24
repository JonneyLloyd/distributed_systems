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
    
    public boolean removeStock(Product product, int qty);
    
}
