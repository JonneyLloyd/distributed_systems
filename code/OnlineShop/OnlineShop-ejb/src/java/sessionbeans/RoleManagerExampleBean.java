/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Role;
import entitysessionbeans.RoleFacadeLocal;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.transaction.UserTransaction;

/**
 *
 * @author oligavin
 */
@Stateless
@LocalBean
public class RoleManagerExampleBean {

//    @EJB
//    private RoleFacadeLocal roleFacade;
    
    private Role r;
    
    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
//    @Transactional(value=TxType.REQUIRED)
    public void save() {
        System.out.println("testing role beans");
        
        r = new Role(10);
        r.setRole("TESTER");
//        roleFacade.create(r);

        em.persist(r);
        em.flush();
    }
    
}
