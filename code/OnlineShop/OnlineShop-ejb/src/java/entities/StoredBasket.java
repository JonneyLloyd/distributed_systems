/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    , @NamedQuery(name = "StoredBasket.findById", query = "SELECT s FROM StoredBasket s WHERE s.id = :id")
    , @NamedQuery(name = "StoredBasket.findByProductId", query = "SELECT s FROM StoredBasket s WHERE s.productId = :productId")
    , @NamedQuery(name = "StoredBasket.findByQty", query = "SELECT s FROM StoredBasket s WHERE s.qty = :qty")})
public class StoredBasket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id", nullable = false)
    private int productId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty", nullable = false)
    private int qty;
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Product product;
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private UserBasket userBasket;

    public StoredBasket() {
    }

    public StoredBasket(Integer id) {
        this.id = id;
    }

    public StoredBasket(Integer id, int productId, int qty) {
        this.id = id;
        this.productId = productId;
        this.qty = qty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoredBasket)) {
            return false;
        }
        StoredBasket other = (StoredBasket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StoredBasket[ id=" + id + " ]";
    }
    
}
