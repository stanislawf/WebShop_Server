/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Article;
import info.novatec.webshop.entities.Category;
import info.novatec.webshop.persistence.CategoryManager;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author stanislawfreund
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CategoryService implements CategoryManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) em.createNamedQuery("Category.findAllCategories").getResultList();
    }

    @Override
    public List<Article> getAllArticleByCategoryName(String name) {
        return (List<Article>) em.createNamedQuery("Category.findAllArticlesByCategoryName").setParameter("name", name).getResultList();
    }

    @Override
    public Category getCategoryByID(Long id) {
        return (Category) em.createNamedQuery("Category.findCategoryByID").setParameter("id", id).getSingleResult();
    }

    @Override
    public boolean createCategory(Category category) {
        em.persist(category);
        return em.contains(category);
    }

    @Override
    public Category getCategoryByNAme(String name) {
        return (Category) em.createNamedQuery("Category.findCategoryByName").setParameter("name", name).getSingleResult();
    }
}
