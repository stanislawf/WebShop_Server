/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sf
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Orders.findOrdersByID", query = "SELECT orders FROM Orders orders WHERE Orders.id = :id"),
            @NamedQuery(name = "Orders.findOrdersByAccount", query = "SELECT orders FROM Orders orders WHERE Orders.account = :account")

})
public class Orders implements Serializable {
    
      //--------------------Attribute & Relationen--------------------//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderDate;
        
    @NotNull
    private double  totalPrice;
    
   
    @ManyToOne(targetEntity = Account.class)
    private Account account;
    
 
    //@NotNull adden
    @OneToMany(targetEntity = OrderLine.class, mappedBy = "order")
    private List<OrderLine> orderLines;
    
    @NotNull
    @ManyToOne(targetEntity = Address.class)
    private Address deliveryAddress;
    
    @NotNull
    @ManyToOne(targetEntity = Address.class)
    private Address billingAddress;
    
    @ManyToOne(targetEntity = CreditCard.class, optional = true)    
    private CreditCard creditCard;
    
    @NotNull
    @ManyToOne(targetEntity = Bill.class)
    private Bill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(List<OrderLine> orderlines) {
        for(OrderLine orderline : orderlines){
            this.totalPrice += (orderline.getQuantity() * orderline.getArticle().getPrice());
        }
    }
    
      public void setTotalPrice(double price) {
        this.totalPrice = price;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    
}
