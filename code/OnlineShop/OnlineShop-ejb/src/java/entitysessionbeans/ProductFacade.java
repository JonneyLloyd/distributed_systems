/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.Catagory;
import entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author oligavin
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless
@LocalBean
public class ProductFacade extends AbstractFacade<Product> implements ProductFacadeLocal {

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
    
}
