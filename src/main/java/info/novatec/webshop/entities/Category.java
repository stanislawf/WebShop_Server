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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sf
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Category.findAllArticlesByCategoryName", query = "SELECT cat.articles FROM Category cat WHERE cat.name = :name"),
    @NamedQuery(name = "Category.findAllCategories", query = "SELECT cat FROM Category cat"),
    @NamedQuery(name = "Category.findCategoryByCategoryID", query = "SELECT cat FROM Category cat WHERE cat.id = :id"),
    @NamedQuery(name = "Category.findCategoryByCategoryName", query = "SELECT cat FROM Category cat WHERE cat.name = :name")
})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    
    @ManyToMany(mappedBy = "categories")
    private List<Article> articles;

    public Category() {}    
    
    public Category(String name) {
        this.name = name;
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

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
