/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oligavin
 */
@Entity
@Table(name = "stored_basket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StoredBasket.findAll", query = "SELECT s FROM StoredBasket s")
    , @NamedQuery(name = "StoredBasket.findByUserBasketId", query = "SELECT s FROM StoredBasket s WHERE s.storedBasketPK.userBasketId = :userBasketId")
    , @NamedQuery(name = "StoredBasket.findByProductId", query = "SELECT s FROM StoredBasket s WHERE s.storedBasketPK.productId = :productId")
    , @NamedQuery(name = "StoredBasket.findByQty", query = "SELECT s FROM StoredBasket s WHERE s.qty = :qty")})
public class StoredBasket implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StoredBasketPK storedBasketPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty", nullable = false)
    private int qty;
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "user_basket_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserBasket userBasket;

    public StoredBasket() {
    }

    public StoredBasket(StoredBasketPK storedBasketPK) {
        this.storedBasketPK = storedBasketPK;
    }

    public StoredBasket(StoredBasketPK storedBasketPK, int qty) {
        this.storedBasketPK = storedBasketPK;
        this.qty = qty;
    }

    public StoredBasket(int userBasketId, int productId) {
        this.storedBasketPK = new StoredBasketPK(userBasketId, productId);
    }

    public StoredBasketPK getStoredBasketPK() {
        return storedBasketPK;
    }

    public void setStoredBasketPK(StoredBasketPK storedBasketPK) {
        this.storedBasketPK = storedBasketPK;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserBasket getUserBasket() {
        return userBasket;
    }

    public void setUserBasket(UserBasket userBasket) {
        this.userBasket = userBasket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storedBasketPK != null ? storedBasketPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoredBasket)) {
            return false;
        }
        StoredBasket other = (StoredBasket) object;
        if ((this.storedBasketPK == null && other.storedBasketPK != null) || (this.storedBasketPK != null && !this.storedBasketPK.equals(other.storedBasketPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StoredBasket[ storedBasketPK=" + storedBasketPK + " ]";
    }
    
}
