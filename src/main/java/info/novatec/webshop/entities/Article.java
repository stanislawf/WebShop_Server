/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
  @NamedQuery(name = "Article.findAllArticles", query ="SELECT article FROM Article article"),
   @NamedQuery(name="Article.findArticleByID", query ="SELECT article FROM Article article WHERE article.id = :id"),
    @NamedQuery(name="Article.findArticleByName", query ="SELECT article FROM Article article WHERE article.name = :name")

})
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    
    @NotNull
    @Size(max = 5000)
    private String description;
    
    private byte[] image;
 
    @NotNull
    private double price;
    
    
    @ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
    private List<Category> categories;
    
    
    @OneToMany(targetEntity = OrderLine.class, mappedBy = "article")
    private List<OrderLine> orderLines;

    public Article(){}

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

//    @Override
//    public String toString() {
//        return "Article{" + "id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + ", price=" + price + ", categories=" + categories.get(0).getName() + ", orderLines=" + orderLines + '}';
//    }
    
    
    
}
