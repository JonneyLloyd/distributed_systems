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
    String catagory;

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    /**
     * Creates a new instance of ProductViewer
     */
    public ProductViewer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Product> getProductByName(){
        return productViewerBean.getProductByName(this.name);
    }
    
    public List<Product> searchProductByName(){
        return productViewerBean.searchProductByName(this.name);
    }
    
    
    
    public List<Product> getAllProducts() {
        return productViewerBean.getAllProducts();
    }
    public List<Product> getAllProductsByCatagory(){
        return productViewerBean.getAllProductsByCategory(this.catagory);
    }
}
