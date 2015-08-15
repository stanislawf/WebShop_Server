/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sf
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Article.findAllArticles", query = "SELECT art FROM Article art"),
    @NamedQuery(name = "Article.findArticleByArticleID", query = "SELECT art FROM Article art WHERE art.id = :id"),
    @NamedQuery(name = "Article.findArticleByArticleName", query = "SELECT art FROM Article art WHERE art.name = :name")

})
public class Article implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(max = 5000)
    @Column(length = 5000)
    private String description;


    private byte[] image;

    @NotNull
    private double price;
 
    @NotNull
    @ManyToMany
    @JoinTable(
      name="article_category",
      joinColumns={@JoinColumn(name="articleID", referencedColumnName="id")},
      inverseJoinColumns={@JoinColumn(name="categoryID", referencedColumnName="id")})
    private List<Category> categories;

  
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL, targetEntity = OrderLine.class, mappedBy = "article")
    private List<OrderLine> orderLines;

    public Article() {
    }

    public Article(String name, String description, double price, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = categories;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
