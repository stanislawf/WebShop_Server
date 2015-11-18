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
   
     @NamedQuery(name = "Bill.findBillByBillID", query = "SELECT bl FROM Bill bl WHERE bl.id = :id"),
     @NamedQuery(name = "Bill.findBillByAccountOwner", query = "SELECT bl FROM Bill bl WHERE bl.accountOwner = :accountOwner"),
     @NamedQuery(name = "Bill.findBillByAccountNumber", query = "SELECT bl FROM Bill bl WHERE bl.accountNumber = :accountNumber"),
     @NamedQuery(name = "Bill.findBillByAccountNumberAndOwner", query = "SELECT bl FROM Bill bl WHERE bl.accountNumber = :accountNumber AND bl.accountOwner =:accountOwner"),
})
public class Bill implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String accountOwner;
    
    @NotNull
    private Long accountNumber;
    
    @NotNull
    private int bankCode;
    
    @NotNull
    private String bankName;
    
    
    @ElementCollection
    @OneToMany (targetEntity = PurchaseOrder.class, mappedBy = "bill")
    private List<PurchaseOrder> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<PurchaseOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<PurchaseOrder> orders) {
        this.orders = orders;
    }
}
