/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Article;
import info.novatec.webshop.persistence.ArticleManager;
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
public class ArticleService implements ArticleManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;

    @Override
    public List<Article> getAllArticles() {
        return (List<Article>) em.createNamedQuery("Article.findAllArticles").getResultList();
    }

    @Override
    public Article getArticleById(Long id) {
        return (Article) em.createNamedQuery("Article.findArticleByID").setParameter("id", id).getSingleResult();
    }

    @Override
    public boolean createArticle(Article article) {
        em.persist(article);
        return em.contains(article);
    }

    @Override
    public Article getArticleByName(String name) {
         return (Article) em.createNamedQuery("Article.findArticleByName").setParameter("name", name).getSingleResult();
  }

}