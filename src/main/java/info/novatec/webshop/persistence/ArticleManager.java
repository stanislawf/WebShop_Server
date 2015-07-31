/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.persistence;

import info.novatec.webshop.entities.Article;
import java.util.List;

/**
 *
 * @author stanislawfreund
 */
public interface ArticleManager {

    public List<Article> getAllArticles();

    public Article getArticleById(Long id);
    
     public Article getArticleByName(String name);
    
    public boolean createArticle(Article article);
}
