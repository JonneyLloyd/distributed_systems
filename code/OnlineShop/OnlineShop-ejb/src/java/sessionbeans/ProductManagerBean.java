/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
import entities.Catagory;
import entities.Stock;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import java.util.logging.Logger;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author jonney
 */
@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
@LocalBean
public class ProductManagerBean implements ProductManager {
    private static final Logger LOGGER = Logger.getLogger(
    Thread.currentThread().getStackTrace()[0].getClassName() );

    private Product p;
    private Catagory c;
    private Stock s;
    
    @Resource
    private UserTransaction transaction;
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
    /**
     * Adds a product to product table. If its category doesn't exist then
     * it will add that to the category table.
     * @param catagory string containing the category name
     * @param name string containing the product name
     * @param description string containing the product description
     * @param cost double containing the products cost
     * @param qty the quantity to add
     * @return returns true/false depending on successful database add
     */
    @Override
    public boolean addProduct(String catagory, String name, String description,
                              double cost, int qty) {
        LOGGER.info("Adding product");
        Query query = em.createNamedQuery("Product.findByName");
        //setting the provided parameters on the query
        query.setParameter("name", name);
        //return result of query
        List<Product> productMatch =  query.getResultList();
        if (productMatch.isEmpty()){
            Query query2 = em.createNamedQuery("Catagory.findByDescription");
            //setting the provided parameters on the query
            query2.setParameter("description", catagory);
            //return result of query
            List<Catagory> catagoryMatch =  query2.getResultList();
            
            
            try{
                transaction.begin();
                
                if (catagoryMatch.isEmpty()){
                    c = new Catagory(null, catagory);
                    em.persist(c);
                    em.flush();
                }
                else
                    c = catagoryMatch.get(0);

                p = new Product(null, name, description, (float) cost);
                p.setCatagoryId(c);
                em.persist(p);
                em.flush();

                s = new Stock(p.getId(), qty);

                em.persist(s);
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
        else
        {
            LOGGER.info("Product already exists");
            return false;
        }
    }
    
    /**
     * Adds a category to category table
     * @param catagory string containing the category name
     * @return returns true/false depending on successful database add
     */
    @Override
    public boolean addCatagory(String catagory) {
        LOGGER.info("Adding catagory");
        
        Query query = em.createNamedQuery("Catagory.findByDescription");
        //setting the provided parameters on the query
        query.setParameter("description", catagory);
        //return result of query
        List<Catagory> catagoryMatch =  query.getResultList();
        if (catagoryMatch.isEmpty()){
            c = new Catagory(null, "newitem");
            em.persist(c);
            em.flush();
            return true;
        }
        return false;
    }
    
    /**
     * Removes a category entry from the database
     * @param catagory string containing the category name 
     * @return returns true/false depending on successful database add
     */
    @Override
    public boolean removeCatagory(String catagory) {
        LOGGER.info("Removing catagory");
        
        Query query = em.createNamedQuery("Catagory.findByDescription");
        //setting the provided parameters on the query
        query.setParameter("description", catagory);
        //return result of query
        List<Catagory> catagoryMatch =  query.getResultList();
        if (!catagoryMatch.isEmpty()){
            c = catagoryMatch.get(0);
            em.remove(c);
            em.flush();
            return true;
        }
        return false;
    }
    
}
