/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import javax.ejb.Local;

/**
 *
 * @author jonney
 */
@Local
public interface ProductManager {
    
    /**
     * Adds a product to product table. If its category doesnt exist then
     * it will add that to the category table.
     * @param catagory string containing the category name
     * @param name string containing the product name
     * @param description string containing the product description
     * @param cost double containing the products cost
     * @return returns true/false depending on successful database add
     */
    public boolean addProduct(String catagory, String name, String description,
                            double cost);
    
    /**
     * Adds a category to category table
     * @param catagory string containing the category name
     * @return returns true/false depending on successful database add
     */
    public boolean addCatagory(String catagory);
    
    /**
     * Removes a category entry from the database
     * @param catagory string containing the category name 
     * @return returns true/false depending on successful database add
     */
    public boolean removeCatagory(String catagory);

    /**
     * Removes a product entry from the database
     * @param id int product id
     * @return returns true/false depending on successful database add
     */
    public boolean removeProduct(int id);
 
}
