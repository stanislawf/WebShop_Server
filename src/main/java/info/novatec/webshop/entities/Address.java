/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
        @NamedQuery(name = "Address.findAddressByAccount", query = "SELECT addr FROM Address addr WHERE :accounts member of addr.accounts"),
          @NamedQuery(name = "Address.findAddressByStreet", query = "SELECT addr FROM Address addr WHERE addr.street = :street"),
            @NamedQuery(name = "Address.findAddressByHomeAddress", query = "SELECT addr FROM Address addr WHERE addr.ishomeAddress = :flag AND addr.accounts = :accounts"),
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
    private boolean ishomeAddress;
    
    @NotNull
    @ManyToMany(mappedBy = "addresses", targetEntity = AccountUser.class)
    private List<Account> accounts;
    
    
    @ElementCollection
    @OneToMany(targetEntity = PurchaseOrder.class, mappedBy = "deliveryAddress")    
    private List<PurchaseOrder> deliveryOrder;
    
    @ElementCollection
    @OneToMany(targetEntity = PurchaseOrder.class, mappedBy = "billingAddress")
    private List<PurchaseOrder> billingOrder;

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

    public List<Account> getAccount() {
        return accounts;
    }

    public void setAccount(List<Account> accounts) {
        this.accounts = accounts;
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

    public boolean isIshomeAddress() {
        return ishomeAddress;
    }

    public void setIshomeAddress(boolean ishomeAddress) {
        this.ishomeAddress = ishomeAddress;
    }
    
    
}
