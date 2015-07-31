/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.persistence;

/**
 *
 * @author stanislawfreund
 */

import info.novatec.webshop.entities.Article;
import info.novatec.webshop.entities.Category;
import java.util.List;

public interface CategoryManager {
  
   public List<Category> getAllCategories();

  public List<Article> getAllArticleByCategoryName(String name);
  
  public Category getCategoryByID(Long id);
  
   public Category getCategoryByNAme(String name);
  
  public boolean createCategory(Category category);
}
