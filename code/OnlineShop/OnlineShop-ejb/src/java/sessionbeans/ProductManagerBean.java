/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
import entities.Catagory;
import entitysessionbeans.ProductFacadeLocal;
import entitysessionbeans.CatagoryFacadeLocal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonney
 */
@Stateless
@LocalBean
public class ProductManagerBean {

    private Product p;
    private Catagory c;
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
    public void test() {
        System.out.println("testing role beans");
        
        Catagory c = new Catagory();
        
        Query query = em.createNamedQuery("Catagory.findByDescription");
        //setting the provided parameters on the query
        query.setParameter("description", "newitem");
        //return result of query
        List<Catagory> catagoryMatch =  query.getResultList();
        if (catagoryMatch.isEmpty()){
            c = new Catagory(null, "newitem");
            em.persist(c);
            em.flush();
        }
        else
            c = catagoryMatch.get(0);
        
        p = new Product(null, "newthing", "Something we sell", (float) 9.99);
        p.setCatagoryId(c);
 
        em.persist(p);
        em.flush();
    }
    
    public void addProduct(String catagory, String name, String description,
                            double cost) {
        System.out.println("Adding product");
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
        }
        else
            System.out.println("Product already exists");
    }
    
    public void addCatagory(String catagory) {
        System.out.println("Adding catagory");
        
        Query query = em.createNamedQuery("Catagory.findByDescription");
        //setting the provided parameters on the query
        query.setParameter("description", catagory);
        //return result of query
        List<Catagory> catagoryMatch =  query.getResultList();
        if (catagoryMatch.isEmpty()){
            c = new Catagory(null, "newitem");
            em.persist(c);
            em.flush();
        }
    }
    
    public void removeCatagory(String catagory) {
        System.out.println("Removing catagory");
        
        Query query = em.createNamedQuery("Catagory.findByDescription");
        //setting the provided parameters on the query
        query.setParameter("description", catagory);
        //return result of query
        List<Catagory> catagoryMatch =  query.getResultList();
        if (!catagoryMatch.isEmpty()){
            c = catagoryMatch.get(0);
            em.remove(c);
            em.flush();
        }
    }
    
    public void removeProduct(String name) {
        System.out.println("Removing product");
        
        Query query = em.createNamedQuery("Product.findByName");
        //setting the provided parameters on the query
        query.setParameter("name", name);
        //return result of query
        List<Product> productMatch =  query.getResultList();
        if (!productMatch.isEmpty()){
            p = productMatch.get(0);
            em.remove(p);
            em.flush();
        }
    }
    
    
}
