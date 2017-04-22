/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;


import entities.Sale;
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

    @Override
    public List<Sale> findByFilter(Integer userId, Integer productId, Date date) {
     CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Sale> cq = cb.createQuery(Sale.class);
        Root<Sale> e = cq.from(Sale.class);
        
        List<Predicate> predicates = new ArrayList<>();
        if (userId != null) {
          predicates.add(cb.equal(e.get("userId"), userId));
        }
        if (productId != null) {
          predicates.add(cb.equal(e.get("productId"), productId));
        }
        if (date != null) {
          predicates.add(cb.equal(e.get("date"), date));
        }

        // AND all of the predicates together:
        if (!predicates.isEmpty())
          cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return getEntityManager().createQuery(cq).getResultList();   
    }

    
    
 
    
}
