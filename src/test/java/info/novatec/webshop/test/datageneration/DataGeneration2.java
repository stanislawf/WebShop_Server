/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.test.datageneration;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.AccountRole;
import info.novatec.webshop.entities.AccountUser;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.Article;
import info.novatec.webshop.entities.Bill;
import info.novatec.webshop.entities.Category;
import info.novatec.webshop.entities.OrderLine;
import info.novatec.webshop.entities.PurchaseOrder;
import info.novatec.webshop.enums.RoleType;
import info.novatec.webshop.helpers.LoadArticleProperties;
import info.novatec.webshop.helpers.PasswordEncryption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author sf
 */
public class DataGeneration2 {

    private EntityManagerFactory emf;
    private EntityManager em;
    private AccountRole user;
    private AccountRole admin;
    private AccountUser accountUser;
    private Address address;
    private Category category;
    private Article article;
    private PurchaseOrder order;

    public DataGeneration2() {
    }

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("test");
        em = emf.createEntityManager();
        user = new AccountRole();
        admin = new AccountRole();
        accountUser = new AccountUser();
        address = new Address();
        category = new Category();
        article = new Article();
        order = new PurchaseOrder();
        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        em.close();
    }

    @Test
    public void persistAccountUserWithOrder() {
        testPersistAccountRole();
        testPersistCategoriesAndArticles();
        testPersistAccountUserWithAddressAndRole();
        testCreateOrder();
    }

    private void testPersistAccountRole() {

        user.setRoleType(RoleType.User);
        admin.setRoleType(RoleType.Admin);

        List<AccountUser> accountUsers = new ArrayList();
        List<Address> accountAddresses = new ArrayList();
        accountAddresses.add(address);
        accountUser.setAddresses(accountAddresses);

        accountUsers.add(accountUser);

        user.setAccount(accountUsers);
        admin.setAccount(accountUsers);

        AccountRole userRole = findAccountRoleByRoleType(RoleType.User);
        AccountRole adminRole = findAccountRoleByRoleType(RoleType.Admin);

        if (userRole == null) {
            em.persist(user);
            assertTrue("Database does not contain role use", em.contains(user));
        }
        if (adminRole == null) {
            em.persist(admin);
            assertTrue("Database does not contain role admin", em.contains(admin));
        }

        em.getTransaction().commit();

    }

    private AccountRole findAccountRoleByRoleType(RoleType roleType) {
        AccountRole role = null;
        try {
            role = (AccountRole) em.createNamedQuery("Role.findRoleByRoleType").setParameter("roleType", roleType).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }

    private void testPersistAccountUserWithAddressAndRole() {

        AccountRole userRole = findAccountRoleByRoleType(RoleType.User);
        AccountRole adminRole = findAccountRoleByRoleType(RoleType.Admin);

        accountUser.setFirstName("accountUserWithAddAndRoleFirstName");
        accountUser.setLastName("accountUserWithAddAndRoleLastName");
        accountUser.setPhoneNumber("0172/4561321");
        accountUser.setEmail("accountUserWithAddAndRole@email.de");
        accountUser.setPassword(PasswordEncryption.securePassword("password"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse("25-06-1987", formatter);
        accountUser.setBirthday(birthDate);
        accountUser.setIsActive(true);

        List<AccountRole> accountRoles = new ArrayList();
        accountRoles.add(userRole);
        accountRoles.add(adminRole);
        accountUser.setRoles(accountRoles);

        address.setStreet("Teststrasse 13");
        address.setCity("Winnenden");
        address.setZipCode("71364");
        address.setCountry("Deutschland");

        List<Address> addresses = new ArrayList();
        addresses.add(address);

        accountUser.setAddresses(addresses);
        List<Account> accounts = new ArrayList();
        accounts.add(accountUser);
        address.setAccount(accounts);

        em.getTransaction().begin();

        AccountUser userAccount = findAccountByAccountUserEmail("accountUserWithAddAndRole@email.de");

        if (userAccount == null) {
            em.persist(accountUser);
            em.persist(address);
            assertTrue("Database does not contain this account", em.contains(accountUser));
            assertTrue("Database does not contain this address", em.contains(address));
        }
        em.getTransaction().commit();

    }

    private AccountUser findAccountByAccountUserEmail(String accountUserOnlyEmail) {
        AccountUser user = null;
        try {
            user = (AccountUser) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", accountUserOnlyEmail).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    private void testPersistCategoriesAndArticles() {
        em.getTransaction().begin();
        //Smartphones
        Category categorySmartphone = new Category();
        categorySmartphone.setName("Smartphones");

        category = getCategoryByNAme(categorySmartphone.getName());
        if (category == null) {
            em.persist(categorySmartphone);
            assertTrue("Database does not contain this category" + categorySmartphone.getName(), em.contains(categorySmartphone));
        }

        //Notebooks
        Category categoryNotebooks = new Category();
        categoryNotebooks.setName("Notebooks");

        category = getCategoryByNAme(categoryNotebooks.getName());
        if (category == null) {
            em.persist(categoryNotebooks);
            assertTrue("Database does not contain this category" + categoryNotebooks.getName(), em.contains(categoryNotebooks));
        }

        //Cameras
        Category categoryCameras = new Category();
        categoryCameras.setName("Cameras");

        category = getCategoryByNAme(categoryCameras.getName());
        if (category == null) {
            em.persist(categoryCameras);
            assertTrue("Database does not contain this category" + categoryCameras.getName(), em.contains(categoryCameras));
        }

        //Television
        Category categoryTelevision = new Category();
        categoryTelevision.setName("Television");

        category = getCategoryByNAme(categoryTelevision.getName());
        if (category == null) {
            em.persist(categoryTelevision);
            assertTrue("Database does not contain this category" + categoryTelevision.getName(), em.contains(categoryTelevision));
        }

        LoadArticleProperties lAP = new LoadArticleProperties();
        List<Article> articles = lAP.loadArticlePropertiesFromSystem();
        assertThat(articles.size(), is(21));
        Category category;

        for (Article listArticle : articles) {
            category = (Category) em.createNamedQuery("Category.findCategoryByCategoryName").setParameter("name", listArticle.getCategories().get(0).getName()).getResultList().get(0);

            List<Article> categoryArticles = new ArrayList();
            List<Category> articlesCategory = new ArrayList();
            categoryArticles.add(listArticle);
            category.setArticles(categoryArticles);
            em.merge(category);

            assertTrue(em.contains(category));
            articlesCategory.add(category);
            listArticle.setCategories(articlesCategory);

            article = getArticleByName(listArticle.getName());
            if (article == null) {
                em.persist(listArticle);
                assertTrue(em.contains(listArticle));
            }

        }
        em.getTransaction().commit();
    }

    private Category getCategoryByNAme(String name) {
        Category category = null;
        try {
            category = (Category) em.createNamedQuery("Category.findCategoryByCategoryName").setParameter("name", name).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return category;
    }

    private Article getArticleByName(String name) {
        Article article = null;
        try {
            article = (Article) em.createNamedQuery("Article.findArticleByArticleName").setParameter("name", name).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return article;
    }

    private void testCreateOrder() {

        em.getTransaction().begin();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate orderDate = LocalDate.parse("12-08-2015", formatter);
        order.setOrderDate(orderDate);
        order.setTotalPrice(0.0);

        Account account = findAccountByAccountUserEmail("accountUserWithAddAndRole@email.de");
        Assert.assertNotNull(account);
        order.setAccount(account);

        Address address = findAddressByStreet("Teststrasse 13");

        order.setDeliveryAddress(address);
        order.setBillingAddress(address);

        Article article = getArticleByName("Apple iPhone 6");
        OrderLine orderline = new OrderLine();
        List<OrderLine> orderLines = new ArrayList();
        orderLines.add(orderline);

        article.setOrderLines(orderLines);
        orderline.setArticle(article);
        orderline.setOrder(order);
        orderline.setQuantity(2);
        order.setOrderLines(orderLines);
        
        Bill bill = new Bill();
        bill.setAccountOwner(account.getFirstName() + " " + account.getLastName());
        bill.setAccountNumber(Long.parseLong("1234567890"));
        bill.setBankCode(12345678);
        bill.setBankName("Bank A");

        order.setBill(bill);
        
        em.persist(order);
        assertTrue(em.contains(order));

        em.getTransaction().commit();
    }

    private Address findAddressByStreet(String street) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByStreet").setParameter("street", street).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return address;
    }

    private Bill getBillByAccountOwner(String accountOwner) {
        Bill bill = null;
        try {
            bill = (Bill) em.createNamedQuery("Bill.findBillByAccountOwner").setParameter("accountOwner", accountOwner).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return bill;
    }

    private List<PurchaseOrder> getOrderByAccount(Account account) {
        List<PurchaseOrder> accountOrders = null;
        try {
            accountOrders = (List<PurchaseOrder>) em.createNamedQuery("Orders.findOrdersByAccount").setParameter("account", account).getResultList();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return accountOrders;
    }
}
