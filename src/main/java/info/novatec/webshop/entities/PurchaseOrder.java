/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
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
    @NamedQuery(name = "Orders.findOrdersByOrderID", query = "SELECT ord FROM PurchaseOrder ord WHERE ord.id = :id"),
    @NamedQuery(name = "Orders.findOrdersByAccount", query = "SELECT ord FROM PurchaseOrder ord WHERE ord.account = :account")
})
public class PurchaseOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private double totalPrice;

    @NotNull
    @ManyToOne
    private Account account;

    @NotNull
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL, targetEntity = OrderLine.class, mappedBy = "order")
    private List<OrderLine> orderLines;

    @NotNull
    @ManyToOne
    private Address deliveryAddress;

    @NotNull
    @ManyToOne
    private Address billingAddress;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    private CreditCard creditCard;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Bill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(List<OrderLine> orderlines) {
        for (OrderLine orderline : orderlines) {
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
