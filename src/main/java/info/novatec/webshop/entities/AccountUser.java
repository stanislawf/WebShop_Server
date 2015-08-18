/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sf
 */


@Entity
public class AccountUser extends Account implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @NotNull
    private String phoneNumber;
    
    @NotNull
    private String password;
    
    @NotNull
    private LocalDate birthday;
    
    @NotNull
    private boolean isActive;
    
    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name="account_address",
      joinColumns={@JoinColumn(name="accountID", referencedColumnName="id")},
      inverseJoinColumns={@JoinColumn(name="addressID", referencedColumnName="id")})
    private List<Address> addresses;
 
    @NotNull
    @ManyToMany
    @JoinTable(
      name="accountuser_accountrole", 
      joinColumns={@JoinColumn(name="accountID", referencedColumnName="id")},
      inverseJoinColumns={@JoinColumn(name="roleID", referencedColumnName="id")})
    private List<AccountRole> roles;
    
    //-----------Setter & Getter -------------

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> homeAddress) {
        this.addresses = homeAddress;
    }

    public List<AccountRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AccountRole> roles) {
        this.roles = roles;
    }
}
