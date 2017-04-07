/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.UserBasket;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author oligavin
 */
@Local
public interface UserBasketFacadeLocal {

    void create(UserBasket userBasket);

    void edit(UserBasket userBasket);

    void remove(UserBasket userBasket);

    UserBasket find(Object id);

    List<UserBasket> findAll();

    List<UserBasket> findRange(int[] range);

    int count();
    
}
