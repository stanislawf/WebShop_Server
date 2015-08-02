/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.test.datageneration;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.Article;
import info.novatec.webshop.entities.Bill;
import info.novatec.webshop.entities.Category;
import info.novatec.webshop.entities.CreditCard;
import info.novatec.webshop.entities.OrderLine;
import info.novatec.webshop.entities.Orders;
import info.novatec.webshop.entities.Role;
import info.novatec.webshop.helpers.LoadArticleProperties;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author sf
 */
public class DataGeneration {

    //Als 1. AusfÃ¼hren
//    @Test
//    public void testCreateNecessaryData(){
//        testCreateRole();
//        testCreateCategories();
//        testCreateArticles();
//    }
    
//     Als 2. AusfÃ¼hren
//    @Test
//    public void testCreateData(){
//      testCreateAccountWithRole();
//    }
    
//     Als 3. AusfÃ¼hren
//    @Test
//    public void testCreateData(){
//      testCreateBills();
//      testCreateOrders();
//    }
    
//     Als 4. AusfÃ¼hren
//    @Test
//    public void testCreateData(){
//        testCreateOrderLines();
//    }
    
    
    
    
    
    
    private void testCreateRole() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Role roleUser = new Role();
        roleUser.setRoleType("User");

        Role roleAdmin = new Role();
        roleAdmin.setRoleType("Admin");

        em.persist(roleUser);
        em.persist(roleAdmin);

        assertTrue(em.contains(roleAdmin));
        assertTrue(em.contains(roleUser));

        em.getTransaction().commit();
        em.close();

    }

    private void testCreateAccountWithRole() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
//        Role role = (Role) em.createNamedQuery("Role.findRoleByRoleType").setParameter("roleType", "User").getSingleResult();

        List<Role> accountRoles = new ArrayList();
//        accountRoles.add(role);

        Role roleAdmin = (Role) em.createNamedQuery("Role.findRoleByRoleType").setParameter("roleType", "Admin").getSingleResult();
        accountRoles.add(roleAdmin);

        Account account = new Account();
        account.setFirstName("Stanislaw");
        account.setLastName("Freund");
        account.setPhoneNumber("0172/3607116");
        account.setEmail("stas.2HG@gmx.net");
        account.setPassword("09876543210");
        String inputStr = "25-06-1987";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date inputDate = null;
        try {
            inputDate = dateFormat.parse(inputStr);
        } catch (ParseException ex) {
            System.err.println("An error occured while creating the role and the account!");
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        account.setBirthday(inputDate);
        account.setIsActive(true);
        account.setRoles(accountRoles);

        Address address = new Address();
        address.setFirstName(account.getFirstName());
        address.setLastName(account.getLastName());
        address.setStreet("TeststraÃŸe 12");
        address.setCity("Winnenden");
        address.setZipCode("71364");
        address.setCountry("Deutschland");
        address.setAccount(account);

        List<Address> addresses = new ArrayList();
        addresses.add(address);

        account.setHomeAddress(addresses);

        em.persist(account);
        em.persist(address);
        assertTrue(em.contains(account));
        assertTrue(em.contains(address));

        em.getTransaction().commit();
        em.close();
    }

    private void testCreateCategories() {
        //---------------------------------------------
        //              Categories
        //---------------------------------------------

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        //Smartphones
        Category categorySmartphone = new Category();
        categorySmartphone.setName("Smartphones");

        em.persist(categorySmartphone);
        assertTrue(em.contains(categorySmartphone));

        //Notebooks
        Category categoryNotebooks = new Category();
        categoryNotebooks.setName("Notebooks");

        em.persist(categoryNotebooks);
        assertTrue(em.contains(categoryNotebooks));

        //Cameras
        Category categoryCameras = new Category();
        categoryCameras.setName("Cameras");

        em.persist(categoryCameras);
        assertTrue(em.contains(categoryCameras));

        //Television
        Category categoryTelevision = new Category();
        categoryTelevision.setName("Television");

        em.persist(categoryTelevision);
        assertTrue(em.contains(categoryTelevision));

        em.getTransaction().commit();
        em.close();

    }

    private void testCreateArticles() {
        //---------------------------------------------
        //              Articles
        //---------------------------------------------

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<Article> articles = LoadArticleProperties.loadArticlePropertiesFromSystem();
        assertThat(articles.size(), is(21));
        Category category;
        for (Article article : articles) {
            category = (Category) em.createNamedQuery("Category.findCategoryByName").setParameter("name", article.getCategories().get(0).getName()).getSingleResult();

            List<Article> categoryArticles = new ArrayList();
            List<Category> articlesCategory = new ArrayList();
            categoryArticles.add(article);
            category.setArticles(categoryArticles);
            em.merge(category);

            assertTrue(em.contains(category));
            articlesCategory.add(category);
            article.setCategories(articlesCategory);
            em.persist(article);
            assertTrue(em.contains(article));
            System.out.println("Commited 3");

        }
        em.getTransaction().commit();
        em.close();
    }

    private void testCreateBills() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Account account = (Account) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", "stas.2HG@gmx.net").getSingleResult();

        Bill bill = new Bill();
        bill.setAccountNumber("87654321");
        bill.setAccountOwner(account.getFirstName() + " " + account.getLastName());
        bill.setBankCode("6206543031");
        bill.setBankName("Bank B");
        em.persist(bill);
        assertTrue(em.contains(bill));

        em.getTransaction().commit();
        em.close();
    }

    private void testCreateOrders() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Orders order = new Orders();

        String inputStr2 = "25-06-1987";
        DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        Date orderDate = null;
        try {
            orderDate = dateFormat2.parse(inputStr2);
        } catch (ParseException ex) {
            System.err.println("An error occured while creating the role and the account!");
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }

        order.setOrderDate(orderDate);
        Account account = (Account) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", "stas.2HG@gmx.net").getSingleResult();
        Address address = (Address) em.createNamedQuery("Address.findAddressByAccountID").setParameter("account", account).getSingleResult();
        List<Orders> accountOrders = new ArrayList();
        accountOrders.add(order);
        account.setOrders(accountOrders);
        List<Orders> addressOrders = new ArrayList();
        addressOrders.add(order);
        address.setDeliveryOrder(addressOrders);
        address.setBillingOrder(accountOrders);
        em.merge(account);
        em.merge(address);

        order.setAccount(account);
        order.setDeliveryAddress(address);
        order.setBillingAddress(address);

        Bill bill = (Bill) em.createNamedQuery("Bill.findBillByAccountOwner").setParameter("accountOwner", (account.getFirstName() + " " + account.getLastName())).getSingleResult();

        List<Orders> billOrders = new ArrayList();
        billOrders.add(order);
        bill.setOrders(billOrders);
        order.setBill(bill);

        order.setTotalPrice(0.0);
        em.persist(order);
        assertTrue(em.contains(order));

        em.getTransaction().commit();
        em.close();

    }

    private void testCreateOrderLines() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        OrderLine orderLine = new OrderLine();
        Article article = (Article) em.createNamedQuery("Article.findArticleByName").setParameter("name", "Apple iPhone 6").getSingleResult();
        List<OrderLine> orderLinesForArticles = new ArrayList();
        orderLinesForArticles.add(orderLine);
        article.setOrderLines(orderLinesForArticles);
        em.merge(article);

        orderLine.setArticle(article);

        Account account = (Account) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", "stas.2HG@gmx.net").getSingleResult();
        Orders order = (Orders) em.createNamedQuery("Orders.findOrdersByAccount").setParameter("account", account).getSingleResult();

        orderLine.setOrder(order);
        orderLine.setQuantity(Byte.valueOf("3"));
        em.persist(orderLine);
        assertTrue(em.contains(orderLine));

        List<OrderLine> orderLines = new ArrayList();
        orderLines.add(orderLine);
        order.setOrderLines(orderLines);
        order.setTotalPrice(orderLines);
        em.merge(order);

        em.persist(orderLine);
        assertTrue(em.contains(orderLine));

        em.getTransaction().commit();
        em.close();
    }

}
