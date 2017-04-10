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
    
    public void test() {
        productManagerBean.addProduct("newtest","newtest", "newtest",5.50);
        System.out.println("testing product add");
        productManagerBean.removeProduct("newtest");
        System.out.println("testing product remove");
    }
    
    public void addNewProduct(String category, String name, String description,
                            double cost){
        productManagerBean.addProduct(category,name, description, cost);
        
    }
    
    public void onSubmitButtonPressed(){
        addNewProduct(this.category,this.name, this.description, this.cost);
    }
    
}
