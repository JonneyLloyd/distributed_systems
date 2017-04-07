/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.Catagory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oligavin
 */
@Stateless
public class CatagoryFacade extends AbstractFacade<Catagory> implements CatagoryFacadeLocal {

    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatagoryFacade() {
        super(Catagory.class);
    }
    
}
