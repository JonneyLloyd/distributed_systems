/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import entities.Product;
import entities.Sale;
import entities.User;
import entitysessionbeans.ProductFacade;
import entitysessionbeans.UserFacade;
import entitysessionbeans.SaleFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author jonney
 */
@Named(value = "salesManager")
@RequestScoped
public class SalesManager implements Serializable {
    
    @EJB
    private SaleFacade saleFacade;
    
    Integer id;
    int qty;
    float price;
    Date date;
    String user;
    String product;
    private List<Sale> saleList = new ArrayList<>();
    
    /**
     * Creates a new instance of SalesManager
     */
    public SalesManager() {
    }
    
    /**
     * Initialiser method
     */
    @PostConstruct
    public void init() {
        saleList = saleFacade.findAll();
    }

    /**
     * Getter for date variable
     * @return Date value
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for date variable
     * @param date Date value
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for id variable
     * @return Integer value
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for id variable
     * @param id Integer value
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for qty variable
     * @return int value
     */
    public int getQty() {
        return qty;
    }

    /**
     * Setter for qty variable
     * @param qty int value
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * Getter for price variable
     * @return float value
     */
    public float getPrice() {
        return price;
    }

    /**
     * Setter for price variable
     * @param price float value
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Getter for userId variable
     * @return Integer value
     */
    public String getUser() {
        return user;
    }

    /**
     * Setter for user variable
     * @param user String value
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter for productId variable
     * @return Integer value
     */
    public String getProduct() {
        return product;
    }

    /**
     * Setter for product variable
     * @param product String value
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Getter for saleList variable
     * @return List of Sale objects
     */
    public List<Sale> getSaleList() {
        return saleList;
    }

    /**
     * Setter for saleList variable
     * @param saleList List of Sale objects
     */
    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }
    
    /**
     * Edit sale entry in database
     * @param sale Sale object
     */
    public void editSale(Sale sale){
        saleFacade.edit(sale);    
    }
    
    /**
     * Add a new sale entry to database
     * @param sale Sale object
     */
    public void addSale(Sale sale){
        saleFacade.create(sale);
    }
    
    /**
     * Delete sale entry from database
     * @param sale Sale object
     */
    public void deleteSale(Sale sale){
        saleFacade.remove(sale);
    }

    /**
     * Filter sales by any combination of userId, productId, date
     */
    public void filterProducts(){
        this.saleList = saleFacade.findByFilter(this.user, this.product, this.date);
        //Fix for issue where date decrements after filter completes
        this.date.setDate(this.date.getDate()+1);
    } 
}
