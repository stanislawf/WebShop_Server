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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author sf
 */
public class DataGeneration {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    private EntityManager em = emf.createEntityManager();

    //--------AccountRoles---------//
    @Test
    public void testPersistAccountRole() {

        em.getTransaction().begin();

        AccountRole roleUser = new AccountRole();
        roleUser.setRoleType(RoleType.User);

        AccountRole roleAdmin = new AccountRole();
        roleAdmin.setRoleType(RoleType.Admin);

        List<AccountUser> accountUsers = new ArrayList();
        AccountUser accountUser = new AccountUser();
        Address address = new Address();
        List<Address> accountAddresses = new ArrayList();
        accountAddresses.add(address);
        accountUser.setAddresses(accountAddresses);

        accountUsers.add(accountUser);

        roleUser.setAccount(accountUsers);
        roleAdmin.setAccount(accountUsers);

        AccountRole userRole = findAccountRoleByRoleType(RoleType.User);
        AccountRole adminRole = findAccountRoleByRoleType(RoleType.Admin);

        if (userRole == null) {
            em.persist(roleUser);
            assertTrue("Database does not contain role use", em.contains(roleUser));
        }
        if (adminRole == null) {
            em.persist(roleAdmin);
            assertTrue("Database does not contain role admin", em.contains(roleAdmin));
        }

        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void testIfAccountRoleExists() {

        AccountRole userRole = findAccountRoleByRoleType(RoleType.User);
        AccountRole adminRole = findAccountRoleByRoleType(RoleType.Admin);
        if (userRole != null) {
            Assert.assertEquals(userRole.getRoleType(), RoleType.User);
        }
        if (adminRole != null) {
            Assert.assertEquals(adminRole.getRoleType(), RoleType.Admin);
        }
    }

    //--------AccountUser---------//
    @Test
    public void testPersistAccountUser() {

        AccountUser accountUser = new AccountUser();
        accountUser.setFirstName("accountUserOnlyFirstName");
        accountUser.setLastName("accountUserOnlyLastName");
        accountUser.setPhoneNumber("0172/4561321");
        accountUser.setEmail("accountUserOny@email.de");
        accountUser.setPassword(PasswordEncryption.securePassword("password"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse("03-06-1991", formatter);
        accountUser.setBirthday(birthDate);
        accountUser.setIsActive(true);

        List<AccountRole> accountRoles = new ArrayList();
        AccountRole userRole = null;
        accountRoles.add(userRole);
        AccountRole adminRole = null;
        accountRoles.add(adminRole);
        accountUser.setRoles(accountRoles);

        Address address = null;
        List<Address> addresses = new ArrayList();
        addresses.add(address);
        accountUser.setAddresses(addresses);

        AccountUser userAccount = findAccountByAccountUserEmail("accountUserOny@email.de");

        em.getTransaction().begin();
        if (userAccount == null) {
            em.persist(accountUser);
            assertTrue("Database does not contain this account", em.contains(accountUser));
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testPersistAddress() {

        Address existentAddress = findAddressByStreet("Teststrasse 12");

        Address address = new Address();
        address.setStreet("Teststrasse 12");
        address.setCity("Teststadt");
        address.setZipCode("71364");
        address.setCountry("Deutschland");
        Account account = null;
        List<Account> accountList = new ArrayList();
        accountList.add(account);
        address.setAccount(accountList);

        em.getTransaction().begin();
        if (existentAddress == null) {
            em.persist(address);
            assertTrue("Database does not contain this address", em.contains(address));
        }
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testPersistAccountUserWithAddressAndRole() {

        AccountRole userRole = findAccountRoleByRoleType(RoleType.User);
        AccountRole adminRole = findAccountRoleByRoleType(RoleType.Admin);

        AccountUser accountUser = new AccountUser();

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

        Address address = new Address();
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
        em.close();
    }

    //--------Guest---------//
    @Test
    public void testPersistGuest() {
        Guest guest = new Guest();
        guest.setEmail("guest@email.de");
        guest.setFirstName("GuestFirstName");
        guest.setLastName("GuestLastName");

        em.getTransaction().begin();
        em.persist(guest);
        assertTrue("Database does not contain this guest", em.contains(guest));
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testPersistCategoriesAndArticles() {
        em.getTransaction().begin();
        //Smartphones
        Category categorySmartphone = new Category();
        categorySmartphone.setName("Smartphones");

        em.persist(categorySmartphone);
        assertTrue("Database does not contain this category" + categorySmartphone.getName(), em.contains(categorySmartphone));

        //Notebooks
        Category categoryNotebooks = new Category();
        categoryNotebooks.setName("Notebooks");

        em.persist(categoryNotebooks);
        assertTrue("Database does not contain this category" + categoryNotebooks.getName(), em.contains(categoryNotebooks));

        //Cameras
        Category categoryCameras = new Category();
        categoryCameras.setName("Cameras");

        em.persist(categoryCameras);
        assertTrue("Database does not contain this category" + categoryCameras.getName(), em.contains(categoryCameras));

        //Television
        Category categoryTelevision = new Category();
        categoryTelevision.setName("Television");

        em.persist(categoryTelevision);
        assertTrue("Database does not contain this category" + categoryTelevision.getName(), em.contains(categoryTelevision));

        LoadArticleProperties lAP = new LoadArticleProperties();
        List<Article> articles = lAP.loadArticlePropertiesFromSystem();
        assertThat(articles.size(), is(21));
        Category category;

        for (Article article : articles) {
            category = (Category) em.createNamedQuery("Category.findCategoryByCategoryName").setParameter("name", article.getCategories().get(0).getName()).getResultList().get(0);

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
        }
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testCreateBills() {
        Bill existentBill = findBillByAccountOwner("Vorname Nachname");

        Bill bill = new Bill();
        bill.setAccountNumber(new Long("01234567894"));
        bill.setAccountOwner("Vorname Nachname");
        bill.setBankCode(12345678);
        bill.setBankName("Bank");

        em.getTransaction().begin();
        if (existentBill == null) {
            em.persist(bill);
            assertTrue(em.contains(bill));
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testCreateOrders() {

        em.getTransaction().begin();

        PurchaseOrder order = new PurchaseOrder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate orderDate = LocalDate.parse("12-08-2015", formatter);
        order.setOrderDate(orderDate);
        order.setTotalPrice(0.0);
        Account account = findAccountByAccountUserEmail("accountUserWithAddAndRole@email.de");
        Assert.assertNotNull(account);
        order.setAccount(account);
        OrderLine orderLine = null;
        List<OrderLine> orderLines = new ArrayList();
        orderLines.add(orderLine);
        order.setOrderLines(orderLines);
        Address address = new Address();
        address.setStreet("TeststraÃƒÅ¸e 123");
        address.setCity("Teststadt");
        address.setZipCode("12312");
        address.setCountry("Deutschland");
        address.setIshomeAddress(false);
        List<Account> accounts = new ArrayList();
        accounts.add(account);
        address.setAccount(accounts);

        order.setDeliveryAddress(address);
        order.setBillingAddress(address);
        Bill bill = new Bill();
        bill.setAccountOwner(account.getFirstName() + " " + account.getLastName());
        bill.setAccountNumber(Long.parseLong("1234567890"));
        bill.setBankCode(12345678);
        bill.setBankName("Bank A");
        order.setBill(bill);

        em.persist(order);
        assertTrue(em.contains(order));

        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void testCreateOrderLines() {
        em.getTransaction().begin();

        OrderLine orderLine = new OrderLine();
        Article article = getArticleByName("Apple iPhone 6");
        Assert.assertNotNull(article);
        List<OrderLine> orderLinesForArticles = new ArrayList();
        orderLinesForArticles.add(orderLine);
        article.setOrderLines(orderLinesForArticles);
        em.merge(article);
        orderLine.setArticle(article);
//
//        Account account = (Account) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", "accountUserWithAddAndRole@email.de").getResultList().get(0);
//        PurchaseOrder order = (PurchaseOrder) em.createNamedQuery("Orders.findOrdersByAccount").setParameter("account", account).getResultList().get(0);
//
//        orderLine.setOrder(order);
//        orderLine.setQuantity(3);
//        em.persist(orderLine);
//        assertTrue(em.contains(orderLine));
//
//        List<OrderLine> orderLines = new ArrayList();
//        orderLines.add(orderLine);
//        order.setOrderLines(orderLines);
//        order.setTotalPrice(orderLines);
//        em.merge(order);
//
//        em.persist(orderLine);
//        assertTrue(em.contains(orderLine));
//
//        em.getTransaction().commit();
//        em.close();
    }

    private AccountRole findAccountRoleByRoleType(RoleType roleType) {
        AccountRole role = null;
        try {
            role = (AccountRole) em.createNamedQuery("Role.findRoleByRoleType").setParameter("roleType", roleType).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }

    private AccountUser findAccountByAccountUserEmail(String accountUserOnlyEmail) {
        AccountUser user = null;
        try {
            user = (AccountUser) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", accountUserOnlyEmail).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
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

    private Bill findBillByAccountOwner(String accountOwner) {
        Bill bill = null;
        try {
            bill = (Bill) em.createNamedQuery("Bill.findBillByAccountOwner").setParameter("accountOwner", accountOwner).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return bill;
    }

    private Address getAddressById(Long id) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByAddressID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return address;
    }

    private List<Address> getAddressByAccounts(@NotNull List<Account> accounts) {
        List<Address> addresses = null;
        try {
            addresses = (List<Address>) em.createNamedQuery("Address.findAddressByAccount").setParameter("accounts", accounts).getResultList();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return addresses;
    }
    
     private Article getArticleByName(String name) {
        Article article = null;
        try {
            article = (Article) em.createNamedQuery("Article.findArticleByArticleName").setParameter("name", name).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(DataGeneration.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return article;
    }
}
