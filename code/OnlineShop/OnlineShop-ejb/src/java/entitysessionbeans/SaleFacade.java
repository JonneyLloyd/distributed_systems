/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.Product;
import entities.Sale;
import entities.User;
import java.util.ArrayList;
import java.util.Date;
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
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleFacade extends AbstractFacade<Sale> implements SaleFacadeLocal {

    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SaleFacade() {
        super(Sale.class);
    }

    /**
     * Search any combination of user, product and date
     * @param user String username
     * @param product String product name
     * @param date Date object
     * @return result of query, List of Sale objects
     */
    @Override
    public List<Sale> findByFilter(String user, String product, Date date) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Sale> cq = cb.createQuery(Sale.class);
        Root<Sale> e = cq.from(Sale.class);
        
        List<Predicate> predicates = new ArrayList<>();
        if (user != null && !user.equals("")) {
          Join<Sale, User> c = e.join("userId", JoinType.LEFT);
          predicates.add(cb.equal(c.get("username"), user));
        }
        if (product != null && !product.equals("")) {
          Join<Sale, Product> c = e.join("productId", JoinType.LEFT);
          predicates.add(cb.equal(c.get("name"), product));
        }
        if (date != null) {
            date.setHours(0);
            Date dateUpper = new Date();
            dateUpper = (Date)date.clone();
            dateUpper.setHours(23);
            dateUpper.setMinutes(59);
            dateUpper.setSeconds(59);
          predicates.add(cb.greaterThanOrEqualTo(e.<Date>get("dateTime"), date));
          predicates.add(cb.lessThanOrEqualTo(e.<Date>get("dateTime"), dateUpper));
        }
        cq.orderBy(cb.desc(e.get("dateTime")));

        // AND all of the predicates together:
        if (!predicates.isEmpty())
          cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return getEntityManager().createQuery(cq).getResultList();   
    }

}
