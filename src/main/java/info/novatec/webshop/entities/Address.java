/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;



/**
 *
 * @author sf
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Address.findAddressByAddressID", query = "SELECT addr FROM Address addr WHERE addr.id = :id"),
        @NamedQuery(name = "Address.findAddressByAccount", query = "SELECT addr FROM Address addr WHERE addr.account = :account"),
          @NamedQuery(name = "Address.findAddressByStreet", query = "SELECT addr FROM Address addr WHERE addr.street = :street"),
          @NamedQuery(name = "Address.findAddressByHomeAddress", query = "SELECT addr FROM Address addr WHERE addr.isHomeAddress = :flag AND addr.account = :account"),
           @NamedQuery(name = "Address.findAddressByStreetAndAccount", query = "SELECT addr FROM Address addr WHERE addr.street = :street AND addr.account = :account")
          
})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String street;
    
    @NotNull
    private String city;
    
    @NotNull
    private String zipCode;
    
    @NotNull
    private String country;
    
    @NotNull
    @ManyToOne
    private Account account;
    
    @NotNull
    private boolean isHomeAddress;
    
    
    @ElementCollection
    @OneToMany(targetEntity = PurchaseOrder.class, mappedBy = "deliveryAddress")    
    private List<PurchaseOrder> deliveryOrder;
    
    @ElementCollection
    @OneToMany(targetEntity = PurchaseOrder.class, mappedBy = "billingAddress")
    private List<PurchaseOrder> billingOrder;

    public boolean isIsHomeAddress() {
        return isHomeAddress;
    }

    public void setIsHomeAddress(boolean isHomeAddress) {
        this.isHomeAddress = isHomeAddress;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accounts) {
        this.account = accounts;
    }

    public List<PurchaseOrder> getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(List<PurchaseOrder> deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<PurchaseOrder> getBillingOrder() {
        return billingOrder;
    }

    public void setBillingOrder(List<PurchaseOrder> billingOrder) {
        this.billingOrder = billingOrder;
    }    



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        return Objects.equals(this.country, other.country);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.street);
        hash = 47 * hash + Objects.hashCode(this.city);
        hash = 47 * hash + Objects.hashCode(this.zipCode);
        hash = 47 * hash + Objects.hashCode(this.country);
        return hash;
    }
    
}
