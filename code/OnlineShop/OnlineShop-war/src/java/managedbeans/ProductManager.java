/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import entities.Product;
import javax.ejb.EJB;
import sessionbeans.ProductManagerBean;

/**
 *
 * @author jonney
 */
@Named(value = "productManager")
@SessionScoped
public class ProductManager implements Serializable {
    @EJB
    private ProductManagerBean productManagerBean;
    
    private String category;
    private String name;
    private String description;
    private double cost;
    private boolean success;
    private boolean complete = false;

    public boolean isSuccess() {
        return success;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    

    /**
     * Creates a new instance of ProductManager
     */
    public ProductManager() {
    }
    
    public String getNotificationDiv(){
        return (this.complete) ? "inherit" : "hidden";
    }
    
    public String getFailNotification() {
        return (this.success) ? "hidden" : "inherit";
    }
    
    public String getSuccessNotification() {
        return (!this.success) ? "hidden" : "inherit";
    }
    
    
    public boolean addNewProduct(String category, String name, String description,
                            double cost){
        complete = true;
        return productManagerBean.addProduct(category,name, description, cost);
        
    }
    
    public boolean removeProduct(String name){
        System.out.println("TEST removeProduct called");
        return productManagerBean.removeProduct(name);
        
    }
    
    public void onSubmitButtonPressed(){
        success = addNewProduct(this.category,this.name, this.description, this.cost);
    }
    
}
