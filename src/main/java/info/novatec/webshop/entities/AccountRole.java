/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import info.novatec.webshop.enums.RoleType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sf
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Role.findRoleByRoleType", query = "SELECT rl FROM AccountRole rl WHERE rl.roleType = :roleType")
})

public class AccountRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @NotNull
    //Das könnte aber auch Null sein. Ein Account muss wissen welche Rolle es hat aber eine Rolle muss ja nicht wissen welche Accounts diese Rolle haben.
    @ManyToMany(mappedBy = "roles")
    private List<AccountUser> account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<AccountUser> getAccount() {
        return account;
    }

    public void setAccount(List<AccountUser> account) {
        this.account = account;
    }
}
