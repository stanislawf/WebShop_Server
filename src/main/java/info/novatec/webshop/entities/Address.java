/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
        @NamedQuery(name = "Address.findAddressByID", query = "SELECT address FROM Address address WHERE address.id = :id"),
    @NamedQuery(name = "Address.findAddressByAccountID", query = "SELECT address FROM Address address WHERE address.account = :account")
})
public class Address implements Serializable {

    
    //--------------------Attribute & Relationen--------------------//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    //Senden an eine andere Person ---> anderes Modell
 
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;
    
    @NotNull
    private String street;
    
    @NotNull
    private String city;
    
    @NotNull
    private String zipCode;
    
    @NotNull
    private String country;
    
    
    @ManyToOne(targetEntity = Account.class)
    private Account account;
    
   
    @OneToMany(targetEntity = Orders.class, mappedBy = "deliveryAddress")    
    private List<Orders> deliveryOrder;
    
   
    @OneToMany(targetEntity = Orders.class, mappedBy = "billingAddress")
    private List<Orders> billingOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Orders> getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(List<Orders> deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<Orders> getBillingOrder() {
        return billingOrder;
    }

    public void setBillingOrder(List<Orders> billingOrder) {
        this.billingOrder = billingOrder;
    }
    

    
}
