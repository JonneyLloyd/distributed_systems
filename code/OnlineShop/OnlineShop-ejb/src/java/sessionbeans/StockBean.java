/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
import entities.Stock;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonney
 */
@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
@LocalBean
public class StockBean implements StockBeanLocal {
    private static final Logger LOGGER = Logger.getLogger(
    Thread.currentThread().getStackTrace()[0].getClassName() );
    
    @Resource
    private UserTransaction transaction;
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;

    @Override
    public boolean removeStock(Product product, int qty) {
        LOGGER.info("Removing stock");
        Query query = em.createNamedQuery("Stock.findByProductId");
        //setting the provided parameters on the query
        query.setParameter("productId", product.getId());
        Stock stock = (Stock)query.getSingleResult();
        if(stock == null)
            return false;
        if(stock.getQty() < qty)
            return false;
        try{
                transaction.begin();
                stock.setQty(stock.getQty()- qty);
                em.merge(stock);
                em.flush();
                transaction.commit();
                return true;
             } catch (Exception e){
                   try {
                       transaction.rollback();
                   } catch (Exception ex) {}
                   return false;
             }
    }

}
