/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Sale;
import entities.StoredBasket;
import entitysessionbeans.SaleFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import sessionbeans.StockBean;
import sessionbeans.UserBasketBean;

/**
 *
 * @author jonney
 */
@Named(value = "checkoutManager")
@RequestScoped
public class CheckoutManager {
    @EJB
    UserBasketBean userBasketBean;
    
    @EJB
    private SaleFacade saleFacade;
    
    @EJB
    private StockBean stockBean;
    
    @Inject 
    UserLoginBean loginBean;
    
    Integer id;
    int qty;
    double total;
    double vat;
    float price;
    Date date;
    String user;
    String product;
    private List<StoredBasket> storedBasket = new ArrayList<>();
    private List<Sale> checkoutList;

    /**
     * Creates a new instance of CheckoutBean
     */
    public CheckoutManager() {
    }
    
    /**
     * Initialiser method
     */
    @PostConstruct
    public void init() {
        storedBasket = (List<StoredBasket>) loginBean.getLoggedInUser().getUserBasket().getStoredBasketCollection();
        checkoutList = new ArrayList<>();
    }

    /**
     * Getter for checkoutList variable
     * @return List of Sale objects
     */
    public List<Sale> getCheckoutList() {
        return checkoutList;
    }

    /**
     * Setter for checkoutList variable
     * @param checkoutList List of Sale objects
     */
    public void setCheckoutList(List<Sale> checkoutList) {
        this.checkoutList = checkoutList;
    }

    /**
     * Getter for total variable
     * @return double value
     */
    public double getTotal() {
        return total;
    }

    /**
     * Setter for total variable
     * @param total double value
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Getter for vat variable
     * @return double value
     */
    public double getVat() {
        return vat;
    }

    /**
     * Setter for vat variable
     * @param vat double value
     */
    public void setVat(double vat) {
        this.vat = vat;
    }
    
    /**
     * Method to remove all items from storedBasket and add to checkoutList as Sales objects
     * @return String value for navigation
     */
    public String confirmButtonPressed(){
        total = 0;
        vat = 0;
        for (StoredBasket storedBasket1 : storedBasket) {
            Sale sale = new Sale();
            sale.setPrice(storedBasket1.getProduct().getPrice() * storedBasket1.getQty());
            sale.setProductId(storedBasket1.getProduct());
            sale.setUserId(loginBean.getLoggedInUser());
            sale.setQty(storedBasket1.getQty());
            sale.setDateTime(new Date());
            if (stockBean.removeStock(storedBasket1.getProduct(), storedBasket1.getQty()))
            {
                total += storedBasket1.getProduct().getPrice() * storedBasket1.getQty();
                checkoutList.add(sale);
                saleFacade.create(sale);
                userBasketBean.removeStoredBasket(storedBasket1);
            }
            
        }
        vat += total * 0.23;
        return "invoice"; 
    }
    
    /**
     * Method for checkout cancel
     * @return String value for navigation
     */
    public String cancelButtonPressed(){
        return "basket";
    }
    
}
