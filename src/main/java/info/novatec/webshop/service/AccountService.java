/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.AccountRole;
import info.novatec.webshop.persistence.AccountManager;
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
 * @author sf
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountService implements AccountManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;

    @Override
    public boolean createAccount(Account account) {
        em.persist(account);
        return em.contains(account);
    }

    @Override
    public Account getAccountByEmail(String email) {
        Account account = null;
        try {
            account = (Account) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", email).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return account;
    }

    @Override
    public boolean createAddress(Address address) {
        em.persist(address);
        return em.contains(address);
    }

    @Override
    public Account getAccountById(Long id) {
        Account account = null;
        try {
            account = (Account) em.createNamedQuery("Account.findAccountByID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return account;
    }

    @Override
    public Address getAddressById(Long id) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByAddressID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return address;
    }
    
    @Override
    public Address getAddressByStreet(String street) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByStreet").setParameter("street", street).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return address;
    }
    
    @Override
    public Address getAddressByHomeAddress(boolean flag, List<Account> accounts) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByHomeAddress").setParameter("flag", flag).setParameter("accounts", accounts).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return address;
    }

    @Override
    public AccountRole getRoleByRoleType(String role) {
        AccountRole roles = null;
        try {
            roles = (AccountRole) em.createNamedQuery("Role.findRoleByRoleType").setParameter("roleType", role).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return roles;
    }

    @Override
    public List<Address> getAddressByAccount(List<Account> accounts) {
        List<Address> addresses = null;
        try {
            addresses = (List<Address>) em.createNamedQuery("Address.findAddressByAccount").setParameter("accounts", accounts).getResultList();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return addresses;
    }

}
