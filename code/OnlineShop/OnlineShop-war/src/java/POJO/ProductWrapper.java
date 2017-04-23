/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import entities.Product;

/**
 * Helper class for adding extra fields to the product viewer which aren't
 * part of the product entity class.
 * Data tables require an object for each row in the table
 */
public class ProductWrapper {
    
    private Product product;
    private int selectedQty = 0;

    /**
     * Constructor for a product wrapper
     * Default selected quantity is zero
     * @param product   The product to wrap
     */
    public ProductWrapper(Product product) {
        this.product = product;
    }

    /**
     * Constructor for a product wrapper with a quantity
     * @param product       The product to wrap
     * @param selectedQty   The selected quantity
     */
    public ProductWrapper(Product product, int selectedQty) {
        this.product = product;
        this.selectedQty = selectedQty;
    }

    /**
     * Get the wrapped product
     * @return  The product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the wrapped product
     * @param product   The product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get the selected quantity of the product wrapped
     * @return  The selected quantity
     */
    public int getSelectedQty() {
        return selectedQty;
    }

    /**
     * Set the selected quantity of the product wrapped
     * @param selectedQty   The quantity to set
     */
    public void setSelectedQty(int selectedQty) {
        this.selectedQty = selectedQty;
    }
    
}
