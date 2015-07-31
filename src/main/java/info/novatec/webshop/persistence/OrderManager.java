/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.persistence;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Orders;

/**
 *
 * @author sf
 */
public interface OrderManager {
       public Orders getOrderById(Long id);
       
         public Orders getOrderByAccount(Account account);
}
