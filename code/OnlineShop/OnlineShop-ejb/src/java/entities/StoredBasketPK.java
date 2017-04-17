/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author oligavin
 */
@Embeddable
public class StoredBasketPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "user_basket_id", nullable = false)
    private int userBasketId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id", nullable = false)
    private int productId;

    public StoredBasketPK() {
    }

    public StoredBasketPK(int userBasketId, int productId) {
        this.userBasketId = userBasketId;
        this.productId = productId;
    }

    public int getUserBasketId() {
        return userBasketId;
    }

    public void setUserBasketId(int userBasketId) {
        this.userBasketId = userBasketId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userBasketId;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoredBasketPK)) {
            return false;
        }
        StoredBasketPK other = (StoredBasketPK) object;
        if (this.userBasketId != other.userBasketId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StoredBasketPK[ userBasketId=" + userBasketId + ", productId=" + productId + " ]";
    }
    
}
