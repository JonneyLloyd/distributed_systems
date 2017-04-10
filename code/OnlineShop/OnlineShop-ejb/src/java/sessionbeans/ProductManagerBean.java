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
        query.setParameter("description", "hardware");
        //return result of query
        List<Catagory> catagoryMatch=  query.getResultList();
        if (catagoryMatch.size() == 0){
            c.setDescription("hardware");
            em.persist(c);
            em.flush();
        }
        else
            c = catagoryMatch.get(0);
        
        p = new Product(null, "SomethingElse", "Something we sell", (float) 9.99);
        p.setCatagoryId(c);
 
        em.persist(p);
        em.flush();
    }
}
