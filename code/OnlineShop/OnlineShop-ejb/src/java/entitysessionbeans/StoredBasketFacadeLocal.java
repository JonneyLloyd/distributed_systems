/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.StoredBasket;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author oligavin
 */
@Local
public interface StoredBasketFacadeLocal {

    void create(StoredBasket storedBasket);

    void edit(StoredBasket storedBasket);

    void remove(StoredBasket storedBasket);

    StoredBasket find(Object id);

    List<StoredBasket> findAll();

    List<StoredBasket> findRange(int[] range);

    int count();
    
}
