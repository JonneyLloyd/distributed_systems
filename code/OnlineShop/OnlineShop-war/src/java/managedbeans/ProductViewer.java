/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Product;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.ProductViewerBean;

/**
 *
 * @author jonney
 */
@Named(value = "productViewer")
@SessionScoped
public class ProductViewer implements Serializable {
    @EJB
    private ProductViewerBean productViewerBean;
            
    String name;
    String category;
    
    /**
     * Creates a new instance of ProductViewer
     */
    public ProductViewer() {
    }

    /**
     * Getter for category variable
     * @return String value
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter for category variable
     * @param category String value
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter for name variable
     * @return String value
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name variable
     * @param name String value
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get list of products using name variable
     * @return List of Product objects
     */
    public List<Product> getProductByName(){
        return productViewerBean.getProductByName(this.name);
    }
    
    /**
     * Get list of products similar to name variable
     * @return List of Product objects
     */
    public List<Product> searchProductByName(){
        return productViewerBean.searchProductByName(this.name);
    }
    
    /**
     * Get all products in database
     * @return List of Product objects
     */
    public List<Product> getAllProducts() {
        return productViewerBean.getAllProducts();
    }
    
    /**
     * Get list of products using category variable
     * @return List of Product objects
     */
    public List<Product> getAllProductsByCatagory(){
        return productViewerBean.getAllProductsByCategory(this.category);
    }
}
