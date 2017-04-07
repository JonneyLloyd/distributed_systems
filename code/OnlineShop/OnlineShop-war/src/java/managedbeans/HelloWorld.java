/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Role;
import entitysessionbeans.RoleFacade;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sessionbeans.RoleManagerExampleBean;
//import sessionbeans.RoleManagerExampleBean;

/**
 *
 * @author oligavin
 */
@Named(value="helloWorld")
@SessionScoped
public class HelloWorld implements Serializable {

    @EJB
    private RoleManagerExampleBean roleManagerExampleBean;
    
    private String msg = "Hello World!";
    

    /**
     * Creates a new instance of helloworld
     */
    public HelloWorld(){
    }

    public String getMsg() {
//        setRole("tester");
        save();
        return msg;
    }

//    @PostConstruct
//    public void init() {
//        r = new Role();
//    }
    
    public void save() {
//        r.setRole("Tester");
//        roleFacade.create(r);
        roleManagerExampleBean.save();
        System.out.println("test");
    }
    
//    @PersistenceContext(unitName = "OnlineShop-ejbPU")
//    private EntityManager em;
//    
////    @TransactionAttribute
//    public void setRole(String name){
////        from.getUsers().remove(u);
////        to.getUsers().add(u);
////        u.setSchool(to);
////        getEntityManager().merge(from);
////        getEntityManager().merge(to);
////        getEntityManager().merge(u);
//        
//        em.getTransaction( ).begin( );
//        
//        Role r = new Role(10, name);
////        roleFacade.create(r);
//
//        em.persist( r );
//        em.getTransaction( ).commit( );
//
//        em.close( );
//    }
    
}
