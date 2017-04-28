/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.Catagory;
import entities.Product;
import entities.Stock;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author oligavin
 */
@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
@LocalBean
public class ProductFacade extends AbstractFacade<Product> implements ProductFacadeLocal {
    
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
     * Provides an instance of an entity manager for other local or super class methods.
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor for a Product Facade
     */
    public ProductFacade() {
        super(Product.class);
    }

    /**
     * Get all products from the database which are/aren't deleted
     * @param deleted   Whether to select the deleted products or not
     * @return
     */
    @Override
    public List<Product> findAll(boolean deleted) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> e = cq.from(Product.class);
        cq.where(cb.equal(e.get("deleted"), deleted));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Get all products from the database which match the given criteria.
     * A criteria will be used for filtering if it is not null.
     * @param id        ID of the product
     * @param category  Category in which a product must be
     * @param name      Name which a product must be LIKE
     * @param deleted   Whether to select the deleted products or not
     * @return
     */
    @Override
    public List<Product> findByFilter(Integer id, String category, String name, boolean deleted) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> e = cq.from(Product.class);
        
        List<Predicate> predicates = new ArrayList<>();
        if (id != null) {
          predicates.add(cb.equal(e.get("id"), id));
        }
        if (category != null && !category.equals("")) {
          Join<Product, Catagory> c = e.join("catagoryId", JoinType.LEFT);
          predicates.add(cb.equal(c.get("description"), category));
        }
        if (name != null && !name.equals("")) {
          predicates.add(cb.like(e.get("name"), name));
        }
        predicates.add(cb.equal(e.get("deleted"), deleted));

        // AND all of the predicates together:
        if (!predicates.isEmpty())
          cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Deletes a Product by marking the deleted flag as true.
     * Stock gets set to zero.
     * This allows existing relations to hold in the database.
     * @param product   The product to delete
     */
    @Override
    public void delete(Product product) {
        product.getStock().setQty(0);
        product.setDeleted(true);
        edit(product);
    }
    
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
