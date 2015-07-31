/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Orders;
import info.novatec.webshop.persistence.OrderManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sf
 */
public class OrdersService implements OrderManager{

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;
    
    @Override
    public Orders getOrderById(Long id) {
    return (Orders) em.createNamedQuery("Orders.findOrdersByID").setParameter("id", id).getSingleResult();
   }

    @Override
    public Orders getOrderByAccount(Account account) {
     return (Orders) em.createNamedQuery("Orders.findOrdersByAccount").setParameter("account", account).getSingleResult();
    }
    
}
