/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.persistence;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.PurchaseOrder;
import java.util.List;

/**
 *
 * @author sf
 */
public interface OrderManager {
       public PurchaseOrder getOrderById(Long id);
       
         public List<PurchaseOrder> getOrderByAccount(Account account);
         
          public boolean createOrder(PurchaseOrder order);
}
