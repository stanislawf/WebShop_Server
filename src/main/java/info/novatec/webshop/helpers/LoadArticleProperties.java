/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.helpers;

import info.novatec.webshop.entities.Article;
import info.novatec.webshop.entities.Category;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sf
 */
public class LoadArticleProperties {

    private final String SERVER_PATH = getClass().getResource("/articleData").getFile();

    
    
    public List<Article> loadArticlePropertiesFromSystem() {
        return loadArticles();
    }

    private List<Article> loadArticles() {
        List<Article> articles = new ArrayList<>();
        File file = new File(SERVER_PATH);   
        String[] directoryArray = file.list();
        int index = 0;
        while (index < directoryArray.length) {
            checkSubPath(SERVER_PATH + File.separator + directoryArray[index], articles);
            index++;
        }
        return articles;
    }

    private static List<Article> checkSubPath(String path,  List<Article> articles){
        File file = new File(path);
        String[] directoryArray = file.list();
        Properties prop = new Properties();
        Article article = new Article();
        for (String extension : directoryArray) {
            String absolutePath = path + File.separator + extension;
            if (extension.endsWith(".properties")) {
                try {
                    prop.load(new FileInputStream(absolutePath));
                } catch (IOException exception) {
                    Logger.getLogger(LoadArticleProperties.class.getName()).log(Level.SEVERE, null, exception);
                }
                List<Category> categories = getArticleCategories(prop.getProperty("Categories"));
                article 
                        = new Article(prop.getProperty("Name"), prop.getProperty("Description"), Double.valueOf(prop.getProperty("Price")) , categories);
                articles.add(article);
               
            } else if (extension.endsWith(".jpg")) {
             //  article.setImage(ByteTransformer.getBytesOfFile(absolutePath));
//                Logger.getLogger(LoadArticleProperties.class.getName(), "Picture found");
                article.toString();
            } else {
                checkSubPath(absolutePath, articles);
            }
        }
        return articles;
    }

    private static List<Category> getArticleCategories(String categoryString) {
        List<String> categoryList = Arrays.asList(categoryString.split("\\s*,\\s*"));
        List<Category> categories = new ArrayList();
        Category cat;
        for (String category : categoryList) {
            cat = new Category(category);
            categories.add(cat);
        }
        return categories;
    }

}
