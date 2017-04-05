/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.Catagory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author oligavin
 */
@Local
public interface CatagoryFacadeLocal {

    void create(Catagory catagory);

    void edit(Catagory catagory);

    void remove(Catagory catagory);

    Catagory find(Object id);

    List<Catagory> findAll();

    List<Catagory> findRange(int[] range);

    int count();
    
}
