/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jonney
 */
@Local
public interface ProductViewer {
    
    /**
     * Get a product by its name
     * 
     * @param name string containing name of the product
     * @return List of Product objects with matching name
     */
    public List<Product> getProductByName(String name);
          
    /**
     * Search for products with name
     * Uses SQL LIKE function for close matches
     * 
     * @param name string containing name of the product
     * @return List of Product objects with similar name
     */
    public List<Product> searchProductByName(String name);
    
    /**
     * Gets all products in the database
     * 
     * @return List object of Product objects
     */
    public List<Product> getAllProducts();
    
    /**
     * Search for products with category
     * 
     * @param category string containing name of the category
     * @return List of Product objects with same category
     */
    public List<Product> getAllProductsByCategory(String category);
    
}
