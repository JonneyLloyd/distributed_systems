/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.UserBasket;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oligavin
 */
@Stateless
public class UserBasketFacade extends AbstractFacade<UserBasket> implements UserBasketFacadeLocal {

    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserBasketFacade() {
        super(UserBasket.class);
    }
    
}
