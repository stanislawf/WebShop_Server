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
import info.novatec.webshop.entities.Guest;
import info.novatec.webshop.entities.OrderLine;
import info.novatec.webshop.entities.PurchaseOrder;
import info.novatec.webshop.enums.RoleType;
import info.novatec.webshop.helpers.LoadArticleProperties;
import info.novatec.webshop.helpers.PasswordEncryption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private Random random;
    private String userEmail;
    private String userStreet;
    private String guestEmail;
    private Guest guest;

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
        random = new Random();
        guest = new Guest();
        em.getTransaction().begin();
        testPersistAccountRole();
        testPersistCategoriesAndArticles();
    }

    @Test
    public void persistAccountUserWithOrder() {
        testPersistAccountUserWithAddressAndRole("einstein", true);
        testCreateOrder(userEmail, userStreet);
    }

    @Test
    public void persistGuestUserWithOrder() {
        testPersistGuest("hobit");
        testCreateOrderForGuest(guestEmail, userStreet);

    }

    @After
    public void tearDown() {
        em.close();
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

    private void testPersistAccountUserWithAddressAndRole(String user, boolean admin) {

        AccountRole userRole = findAccountRoleByRoleType(RoleType.User);
        AccountRole adminRole = findAccountRoleByRoleType(RoleType.Admin);

        accountUser.setFirstName(user + "FirstName");
        accountUser.setLastName(user + "LastName");

        accountUser.setPhoneNumber("0172/4561321");
        accountUser.setEmail(user + "@email.de");
        accountUser.setPassword(PasswordEncryption.securePassword(user));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        String s = generateRandomBirthDate();
        LocalDate birthDate = LocalDate.parse(s, formatter);
        accountUser.setBirthday(birthDate);
        accountUser.setIsActive(true);

        List<AccountRole> accountRoles = new ArrayList();
        if (admin) {
            accountRoles.add(adminRole);
        }
        accountRoles.add(userRole);

        accountUser.setRoles(accountRoles);

        address.setStreet(user + "Teststrasse 13");
        address.setCity(user + "Stadt");
        address.setZipCode(String.valueOf(generateRandomInteger(01000, 95001)));
        address.setCountry("Deutschland");

        List<Address> addresses = new ArrayList();
        addresses.add(address);

        accountUser.setAddresses(addresses);

        address.setAccount(accountUser);

        em.getTransaction().begin();

        AccountUser userAccount = (AccountUser) findAccountByAccountUserEmail(user + "@email.de");

        if (userAccount == null) {
            em.persist(accountUser);
            em.persist(address);
            assertTrue("Database does not contain this account", em.contains(accountUser));
            assertTrue("Database does not contain this address", em.contains(address));
        }
        em.getTransaction().commit();
        userEmail = accountUser.getEmail();
        userStreet = address.getStreet();
    }

    private void testPersistGuest(String guestAdd) {
        guest.setEmail(guestAdd + "@email.de");
        guest.setFirstName(guestAdd + "FirstName");
        guest.setLastName(guestAdd + "LastName");

        address.setStreet(guestAdd + "Teststrasse 13");
        address.setCity(guestAdd + "Stadt");
        address.setZipCode(String.valueOf(generateRandomInteger(01000, 95001)));
        address.setCountry("Deutschland");

        List<Address> addresses = new ArrayList();
        addresses.add(address);

        guest.setAddresses(addresses);

        address.setAccount(guest);

        Guest existentGuest = (Guest) findAccountByAccountUserEmail(guestAdd + "@email.de");

        em.getTransaction().begin();
        if (existentGuest == null) {
            em.persist(guest);
            assertTrue("Database does not contain this guest", em.contains(guest));
        }

        em.getTransaction().commit();
        guestEmail = guest.getEmail();
        userStreet = address.getStreet();

    }

    private void testCreateOrderForGuest(String accountEmail, String street) {
        em.getTransaction().begin();
        String date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        if (LocalDate.now().getMonthValue() < 10) {
            date = generateRandomInteger(10, 29) + "-0" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getYear();
        } else {
            date = generateRandomInteger(10, 29) + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getYear();
        }

        LocalDate orderDate = LocalDate.parse(date, formatter);
        order.setOrderDate(orderDate);
        order.setTotalPrice(0.0);

        Guest guest = (Guest) findAccountByAccountUserEmail(accountEmail);
        Assert.assertNotNull(guest);
        order.setAccount(guest);

        Address guestAddress = findAddressByStreet(street);

        guestAddress.setAccount(guest);

        order.setDeliveryAddress(guestAddress);
        order.setBillingAddress(guestAddress);

        Article article = null;
        while (article == null) {
            article = getArticleById(new Long(generateRandomInteger(7, 28)));
        }
        OrderLine orderline = new OrderLine();
        List<OrderLine> orderLines = new ArrayList();
        orderLines.add(orderline);

        article.setOrderLines(orderLines);
        orderline.setArticle(article);
        orderline.setOrder(order);
        orderline.setQuantity(2);
        order.setOrderLines(orderLines);

        Bill bill = new Bill();
        bill.setAccountOwner(guest.getFirstName() + " " + guest.getLastName());
        bill.setAccountNumber(Long.valueOf(String.valueOf(generateRandomInteger(111111111, 999999999))));
        bill.setBankCode(generateRandomInteger(11111111, 77777777));
        bill.setBankName("Bank B");

        order.setBill(bill);

        em.persist(order);
        assertTrue(em.contains(order));

        em.getTransaction().commit();
    }

    private void testCreateOrder(String accountEmail, String street) {
        em.getTransaction().begin();
        String date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        if (LocalDate.now().getMonthValue() < 10) {
            date = generateRandomInteger(10, 29) + "-0" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getYear();
        } else {
            date = generateRandomInteger(10, 29) + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getYear();
        }

        LocalDate orderDate = LocalDate.parse(date, formatter);
        order.setOrderDate(orderDate);
        order.setTotalPrice(0.0);

        Account account = findAccountByAccountUserEmail(accountEmail);
        Assert.assertNotNull(account);
        order.setAccount(account);

        Address address = findAddressByStreet(street);

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
        bill.setAccountNumber(Long.valueOf(String.valueOf(generateRandomInteger(111111111, 999999999))));
        bill.setBankCode(generateRandomInteger(11111111, 77777777));
        bill.setBankName("Bank A");

        order.setBill(bill);

        em.persist(order);
        assertTrue(em.contains(order));

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

    private Account findAccountByAccountUserEmail(String accountUserOnlyEmail) {
        Account user = null;
        try {
            user = (Account) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", accountUserOnlyEmail).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
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

    private Article getArticleByName(String name) {
        Article article = null;
        try {
            article = (Article) em.createNamedQuery("Article.findArticleByArticleName").setParameter("name", name).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return article;
    }

    private Address findAddressByStreet(String street) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByStreet").setParameter("street", street).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, exeption);
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

    private String generateRandomBirthDate() {
        int day, month, year;
        day = generateRandomInteger(1, 29);
        month = generateRandomInteger(1, 13);
        year = generateRandomInteger(1950, LocalDate.now().getYear() - 18);

        return day + "-" + month + "-" + year;
    }

    private int generateRandomInteger(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public Article getArticleById(Long id) {
        Article article = null;
        try {
            article = (Article) em.createNamedQuery("Article.findArticleByArticleID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration2.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return article;
    }
}
