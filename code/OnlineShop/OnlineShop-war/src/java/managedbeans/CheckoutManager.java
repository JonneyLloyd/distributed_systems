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

    public List<Sale> getCheckoutList() {
        return checkoutList;
    }

    public void setCheckoutList(List<Sale> checkoutList) {
        this.checkoutList = checkoutList;
    }
    
    
    
    public String confirmButtonPressed(){
        for (StoredBasket storedBasket1 : storedBasket) {
            Sale sale = new Sale();
            sale.setPrice(storedBasket1.getProduct().getPrice() * storedBasket1.getQty());
            sale.setProductId(storedBasket1.getProduct());
            sale.setUserId(loginBean.getLoggedInUser());
            sale.setQty(storedBasket1.getQty());
            sale.setDateTime(new Date());
            if (stockBean.removeStock(storedBasket1.getProduct(), storedBasket1.getQty()))
            {
                checkoutList.add(sale);
                saleFacade.create(sale);
                userBasketBean.removeStoredBasket(storedBasket1);

            }
            
        }
        return "invoice"; 
    }
            
    public String cancelButtonPressed(){
        return "basket";
    }
    
}
