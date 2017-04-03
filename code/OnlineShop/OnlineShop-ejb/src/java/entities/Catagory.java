/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oligavin
 */
@Entity
@Table(name = "catagory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catagory.findAll", query = "SELECT c FROM Catagory c")
    , @NamedQuery(name = "Catagory.findById", query = "SELECT c FROM Catagory c WHERE c.id = :id")
    , @NamedQuery(name = "Catagory.findByDesc", query = "SELECT c FROM Catagory c WHERE c.desc = :desc")})
public class Catagory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "desc", nullable = false, length = 45)
    private String desc;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "catagoryId")
    private Product product;

    public Catagory() {
    }

    public Catagory(Integer id) {
        this.id = id;
    }

    public Catagory(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        if (!(object instanceof Catagory)) {
            return false;
        }
        Catagory other = (Catagory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Catagory[ id=" + id + " ]";
    }
    
}
