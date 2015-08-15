/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Article;
import info.novatec.webshop.persistence.ArticleManager;
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
public class ArticleService implements ArticleManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;

    @Override
    public List<Article> getAllArticles() {
        List<Article> articleList = null;
        try {
            articleList = (List<Article>) em.createNamedQuery("Article.findAllArticles").getResultList();
        }  catch (NoResultException exeption) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return articleList;
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = null;
        try {
            article = (Article) em.createNamedQuery("Article.findArticleByArticleID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return article;
    }

    @Override
    public boolean createArticle(Article article) {
        em.persist(article);
        return em.contains(article);
    }

    @Override
    public Article getArticleByName(String name) {
        Article article = null;
        try {
            article = (Article) em.createNamedQuery("Article.findArticleByArticleName").setParameter("name", name).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return article;
    }
}
