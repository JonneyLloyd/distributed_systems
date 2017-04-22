/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Catagory;
import entities.Product;
import entitysessionbeans.CatagoryFacade;
import entitysessionbeans.ProductFacade;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author jonney
 */
@Named(value = "productViewer")
@RequestScoped
public class ProductViewer implements Serializable {
    
    @EJB
    private ProductFacade productFacade;
    
    @EJB
    private CatagoryFacade categoryFacade;
    
    private List<Product> productList = new ArrayList<>();
    private List<Catagory> categoryList = new ArrayList<>();
    
    String name;
    String category;
    Integer id;
    
    /**
     * Creates a new instance of ProductViewer
     */
    public ProductViewer() {
    }

    @PostConstruct
    public void init() {
        productList = productFacade.findAll(false);
        categoryList = categoryFacade.findAll();
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
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Get all products in database
     * @return List of Product objects
     */
    public List<Product> getAllProducts() {
        return this.productList;
    }
    
    /**
     * Getter for categoryList variable
     * @return List of Category objects
     */
    public List<Catagory> getAllCategories() {
        return this.categoryList;
    }
    
    /**
     * Filter products by any combination of id, category, name
     */
    public void filterProducts(){
        boolean deleted = false;
        this.productList = productFacade.findByFilter(this.id, this.category, this.name, deleted);
    }
    
    /**
     * Save a product entry to database
     * @param product Product object
     */
    public void saveProduct(Product product){
        productFacade.edit(product);
    }
    
    /**
     * Remove product entry from database
     * @param product Product object
     */
    public void removeProduct(Product product){
        productFacade.delete(product);
        productList.remove(product);
    }
}
