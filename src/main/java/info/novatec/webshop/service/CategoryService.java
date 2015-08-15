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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        List<Category> categoryList = null;
        try {
            categoryList = (List<Category>) em.createNamedQuery("Category.findAllCategories").getResultList();
        } catch (NoResultException exeption) {
            Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return categoryList;
    }

    @Override
    public List<Article> getAllArticleByCategoryName(String name) {
        List<Article> categoryArticleList = null;
        try {
            categoryArticleList = (List<Article>) em.createNamedQuery("Category.findAllArticlesByCategoryName").setParameter("name", name).getResultList();
        } catch (NoResultException exeption) {
            Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return categoryArticleList;
    }

    @Override
    public Category getCategoryByID(Long id) {
        Category category = null;
        try {
            category = (Category) em.createNamedQuery("Category.findCategoryByCategoryID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return category;
    }

    @Override
//    --> JavaDoc
    public boolean createCategory(Category category) {
        em.persist(category);
        return em.contains(category);
    }

    @Override
    public Category getCategoryByNAme(String name) {
        Category category = null;
        try {
            category = (Category) em.createNamedQuery("Category.findCategoryByCategoryName").setParameter("name", name).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return category;
    }
}
