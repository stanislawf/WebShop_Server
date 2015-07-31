/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.Role;
import info.novatec.webshop.persistence.AccountManager;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
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
        return (Account) em.createNamedQuery("Account.findAccountByEmail").setParameter("email", email).getSingleResult();
 }


  @Override
  public boolean createAddress(Address address) {
    em.persist(address);
    return em.contains(address);
  }

  @Override
  public Account getAccountById(Long id) {
    return (Account) em.createNamedQuery("Account.findAccountByID").setParameter("id", id).getSingleResult();
  }

  @Override
  public Address getAddressById(Long id) {
    return (Address) em.createNamedQuery("Address.findAddressByID").setParameter("id", id).getSingleResult();
   }

  @Override
  public Role getRoleByRoleType(String role) {
    return (Role) em.createNamedQuery("Role.findRoleByRoleType").setParameter("roleType", role).getSingleResult();
  }

  @Override
  public Address getAddressByAccount(Account account) {
    return (Address) em.createNamedQuery("Address.findAddressByAccountID").setParameter("account", account).getSingleResult();
  }

   

}
