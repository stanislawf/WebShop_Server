/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.persistence;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.AccountRole;
import info.novatec.webshop.enums.RoleType;
import java.util.List;

/**
 *
 * @author sf
 */
public interface AccountManager {

  public boolean createAccount(Account account);

  public Account getAccountByEmail(String email);

  public Account getAccountById(Long id);

  public Address getAddressById(Long id);
  
  public Address getAddressByStreet(String street);
  
  public Address getAddressByStreetAndAccount(String street, Account account);
  
   public Address getAddressByHomeAddress(boolean flag, Account account);
  
  public Address getAddressByAccount(Account account);

  public boolean createAddress(Address address);

  public AccountRole getRoleByRoleType(RoleType role);
  
  public boolean isAddressInDB(Address address);
  
  public void updateAddress(Address address);
  
  public void updateAccount(Account account);
  
  public boolean isAccountinDB(Account account);
  
  public Address findAddress(Address address);
  
  public Account findAccount(Account account);
  
}
