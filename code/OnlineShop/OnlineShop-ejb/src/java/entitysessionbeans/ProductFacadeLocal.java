/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitysessionbeans;

import entities.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author oligavin
 */
@Local
public interface ProductFacadeLocal {

    void create(Product product);

    void edit(Product product);

    void remove(Product product);

    Product find(Object id);

    List<Product> findAll();

    List<Product> findRange(int[] range);

    int count();
    
    List<Product> findAll(boolean deleted);
    
    List<Product> findByFilter(Integer id, String category, String name, boolean deleted);

    void delete(Product product);
    
    boolean addProduct(String catagory, String name, String description,
                              double cost, int qty);
    
}
