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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oligavin
 */
@Entity
@Table(name = "user_profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserProfile.findAll", query = "SELECT u FROM UserProfile u")
    , @NamedQuery(name = "UserProfile.findById", query = "SELECT u FROM UserProfile u WHERE u.id = :id")
    , @NamedQuery(name = "UserProfile.findByFName", query = "SELECT u FROM UserProfile u WHERE u.fName = :fName")
    , @NamedQuery(name = "UserProfile.findBySName", query = "SELECT u FROM UserProfile u WHERE u.sName = :sName")
    , @NamedQuery(name = "UserProfile.findByAddressLine1", query = "SELECT u FROM UserProfile u WHERE u.addressLine1 = :addressLine1")
    , @NamedQuery(name = "UserProfile.findByAddressLine2", query = "SELECT u FROM UserProfile u WHERE u.addressLine2 = :addressLine2")
    , @NamedQuery(name = "UserProfile.findByCountry", query = "SELECT u FROM UserProfile u WHERE u.country = :country")
    , @NamedQuery(name = "UserProfile.findByPostcode", query = "SELECT u FROM UserProfile u WHERE u.postcode = :postcode")
    , @NamedQuery(name = "UserProfile.findByMessage", query = "SELECT u FROM UserProfile u WHERE u.message = :message")})
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "f_name", nullable = false, length = 45)
    private String fName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "s_name", nullable = false, length = 45)
    private String sName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "address_line_1", nullable = false, length = 45)
    private String addressLine1;
    @Size(max = 45)
    @Column(name = "address_line_2", length = 45)
    private String addressLine2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "country", nullable = false, length = 45)
    private String country;
    @Size(max = 45)
    @Column(name = "postcode", length = 45)
    private String postcode;
    @Size(max = 45)
    @Column(name = "message", length = 45)
    private String message;
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public UserProfile() {
    }

    public UserProfile(Integer id) {
        this.id = id;
    }

    public UserProfile(Integer id, String fName, String sName, String addressLine1, String country) {
        this.id = id;
        this.fName = fName;
        this.sName = sName;
        this.addressLine1 = addressLine1;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserProfile[ id=" + id + " ]";
    }
    
}
