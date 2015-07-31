/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.persistence;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.Role;

/**
 *
 * @author sf
 */
public interface AccountManager {

  public boolean createAccount(Account account);

  public Account getAccountByEmail(String email);

  public Account getAccountById(Long id);

  public Address getAddressById(Long id);
  
  public Address getAddressByAccount(Account account);

  public boolean createAddress(Address address);

  public Role getRoleByRoleType(String role);
  
}
