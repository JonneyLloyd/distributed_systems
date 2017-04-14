/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Catagory;
import entities.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jonney
 */
@Stateless
@LocalBean
public class ProductViewerBean {
    
    private Product p;
    private Catagory c;
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;

    /**
     * Get a product by its name
     * 
     * @param name string containing name of the product
     * @return List of Product objects with matching name
     */
    public List<Product> getProductByName(String name) {
        // create named query and set parameter
        Query query = em.createNamedQuery(
                "Product.findByName")
                .setParameter("name", name);
        // return query result
        return query.getResultList();
    }
    
    /**
     * Search for products with name
     * Uses SQL LIKE function for close matches
     * 
     * @param name string containing name of the product
     * @return List of Product objects with similar name
     */
    public List<Product> searchProductByName(String name) {
        // create named query and set parameter
        Query query = em.createNamedQuery(
                "Product.searchByName")
                .setParameter("name", name);
        // return query result
        return query.getResultList();
        
        
    }
    
    /**
     * Gets all products in the database
     * 
     * @return List object of Product objects
     */
    public List<Product> getAllProducts() {
        // create named query and set parameter
        Query query = em.createNamedQuery(
                "Product.findAll");
        // return query result
        return query.getResultList();
    }
    
    /**
     * Search for products with category
     * 
     * @param catagory string containing name of the category
     * @return List of Product objects with same category
     */
    public List<Product> getAllProductsByCategory(String category) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Catagory.findByDescription");
            //setting the provided parameters on the query
            query.setParameter("description", category);
            //return result of query
            List<Product> productMatch = null;
            List<Catagory> catagoryMatch =  query.getResultList();
            if (!catagoryMatch.isEmpty()){
                Query query2 = em.createNamedQuery("Product.findByCatagoryId");
                query2.setParameter("catagory_id", catagoryMatch.get(0));
                productMatch =  query2.getResultList();
                return productMatch;
            }
           return productMatch;
    }
}
