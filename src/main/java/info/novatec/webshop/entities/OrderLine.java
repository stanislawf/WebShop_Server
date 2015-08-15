/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sf
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Orderline.findOrderlineByOrderLineID", query = "SELECT orderL FROM OrderLine orderL WHERE orderL.id = :id")
})
public class OrderLine implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private int quantity;

    @NotNull
    @ManyToOne
    private Article article;
    
    @NotNull
    @ManyToOne
    private PurchaseOrder order;

    public Long getId() {
        return id;
    }

    public PurchaseOrder getOrder() {
        return order;
    }

    public void setOrder(PurchaseOrder order) {
        this.order = order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
