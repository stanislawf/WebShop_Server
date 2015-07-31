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
public abstract class LoadArticleProperties {

    private static final String path = "C:\\Users\\sf\\Documents\\NetBeansProjects\\Webshop\\Webshop\\WebShop_Server\\src\\main\\resources\\articleData";
                                  
    public static List<Article> loadArticlePropertiesFromSystem() {
        return loadArticles();
    }

    private static List<Article> loadArticles() {
        List<Article> articles = new ArrayList<>();
        File file = new File(path);
        System.out.println(path);
        String[] directoryArray = file.list();
        System.out.println(directoryArray.length);
        int index = 0;
        
        while (index < directoryArray.length) {
            checkSubPath(path + File.separator + directoryArray[index], articles);
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
            
            System.out.println("------------");
            String absolutePath = path + File.separator + extension;
            System.out.println(absolutePath);
            if ((extension).endsWith(".properties")) {
                System.out.println("True. It´s there");

                try {
                    prop.load(new FileInputStream(absolutePath));
                } catch (IOException ex) {
                    Logger.getLogger(LoadArticleProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
                List<Category> categories = getArticleCategories(prop.getProperty("Categories"));
                article 
                        = new Article(prop.getProperty("Name"), prop.getProperty("Description"), Double.valueOf(prop.getProperty("Price")) , categories);
                articles.add(article);
               
            } else if ((extension).endsWith(".jpg")) {
             //  article.setImage(ByteTransformer.getBytesOfFile(absolutePath));
                System.out.println("Image Found");
                //article.toString();
            } else {
                checkSubPath(absolutePath, articles);
            }
        }
        System.out.println("Articles Size " + articles.size());
        return articles;
    }

    private static List<Category> getArticleCategories(String categoryString) {
        List<String> categoryList = Arrays.asList(categoryString.split("\\s*,\\s*"));
        List<Category> categories = new ArrayList();
        Category cat;
        for (String category : categoryList) {
            System.out.println("I´m Here " + category);
            cat = new Category(category);
            categories.add(cat);
            System.out.println("--asdfasf---");
            System.out.println(cat.getName());
        }
        return categories;
    }

}
