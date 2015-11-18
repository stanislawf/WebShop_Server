/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.AccountRole;
import info.novatec.webshop.enums.RoleType;
import info.novatec.webshop.persistence.AccountManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sf
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountService implements AccountManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

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
            LOGGER.info("Adresse: {}", address.getStreet());
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
    public Address getAddressByStreetAndAccount(String street, Account account) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByStreetAndAccount").setParameter("street", street).setParameter("account", account).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return address;
    }

    @Override
    public Address getAddressByHomeAddress(boolean homeAddress, Account account) {
        Address address = null;
        try {
            address = (Address) em.createNamedQuery("Address.findAddressByHomeAddress").setParameter("flag", homeAddress).setParameter("account", account).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return address;
    }

    @Override
    public AccountRole getRoleByRoleType(RoleType role) {

        AccountRole accountRole = null;
        try {
            accountRole = (AccountRole) em.createNamedQuery("Role.findRoleByRoleType").setParameter("roleType", role).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accountRole;
    }

    @Override
    public Address getAddressByAccount(Account account) {
        Address addresses = null;
        try {
            addresses = (Address) em.createNamedQuery("Address.findAddressByAccount").setParameter("account", account).getResultList();
        } catch (NoResultException exeption) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return addresses;
    }

    @Override
    public boolean isAddressInDB(Address address) {
        return em.contains(address);
    }

    @Override
    public void updateAddress(Address address) {
        em.merge(address);
    }

    @Override
    public void updateAccount(Account account) {
        try{
        Account tempAccount = em.find(Account.class, account.getId());
        List<Address> addresses = account.getAddresses();
        List<Address> tempAddresses = new ArrayList();
        for(Address address: addresses){
            Address tempAddress = em.find(Address.class, address.getId());
            if(tempAddress != null){
                tempAddresses.add(tempAddress);
            }else{
                tempAddresses.add(address);
            }
        }
        account.setAddresses(tempAddresses);
        if (tempAccount != null) {
            tempAccount = account;
            em.merge(tempAccount);
        }
         } catch (IllegalArgumentException exception) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @Override
    public boolean isAccountinDB(Account account) {
        return em.contains(account);
    }

    @Override
    public Address findAddress(Address address) {
        return em.find(Address.class, address.getId());
    }
    
    @Override
    public Account findAccount(Account account) {
        return em.find(Account.class, account.getId());
    }
    
    

}
