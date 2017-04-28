/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import sessionbeans.ProductManagerBean;

/**
 *
 * @author jonney
 */
@Named(value = "productManager")
@RequestScoped
public class ProductManager implements Serializable {
    @EJB
    private ProductManagerBean productManagerBean;
    
    @Inject
    private MessageLogger messageLog;
    
    private String category;
    private String name;
    private String description;
    private double cost;
    private int qty;
    private boolean success;
    private boolean complete = false;
    
    /**
     * Creates a new instance of ProductManager
     */
    public ProductManager() {
    }

    /**
     * Getter for success variable
     * @return value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Getter for complete variable
     * @return value of complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Setter for complete variable
     * @param complete boolean value
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Setter for success variable
     * @param success boolean value
     */
    public void setSuccess(boolean success) {
        this.success = success;
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
     * Getter for description variable
     * @return String value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description variable
     * @param description String value
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for cost variable
     * @return String value
     */
    public double getCost() {
        return cost;
    }

    /**
     * Setter for cost variable
     * @param cost String value
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Getter for quantity variable
     * @return  Quantity
     */
    public int getQty() {
        return qty;
    }

    /**
     * Setter for quantity variable
     * @param qty   Quantity
     */
    public void setQty(int qty) {
        this.qty = qty;
    }
    
    /**
     * Gets display style attribute for success div depending on whether form has been submitted
     * 
     * @return true/false on complete boolean
     */
    public String getNotificationDiv(){
        return (this.complete) ? "inherit" : "hidden";
    }
    
    /**
     * Gets display style attribute for fail notification if product was not added
     * 
     * @return true/false on success boolean
     */
    public String getFailNotification() {
        return (this.success) ? "hidden" : "inherit";
    }
    
    /**
     * Gets display style attribute for success notification if product was added
     * 
     * @return true/false on success boolean
     */
    public String getSuccessNotification() {
        return (!this.success) ? "hidden" : "inherit";
    }
    
    /**
     * Call addProduct in EJB to add product to database
     * 
     * @param category string containing the category name
     * @param name string containing the product name
     * @param description string containing the product description
     * @param cost double containing the products cost
     * @param qty the quantity to add
     * @return returns true/false depending on EJB result
     */
    public boolean addNewProduct(String category, String name, String description,
                                 double cost, int qty){
        complete = true;
        return productManagerBean.addProduct(category,name, description, cost, qty);
        
    }
    
    /**
     * Sets the success variable to the result of calling addNewProduct()
     * Logs to message log that product was added
     */
    public void onSubmitButtonPressed(){
        success = addNewProduct(this.category,this.name, this.description, this.cost, this.qty);
        if (success) {
            messageLog.sendMessageToLog("Added product~" + this.category + "/" + this.name + "/" +this.description + "/" +  this.cost + "/" +  this.qty);
        }
    }
    
}
