/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Account;
import info.novatec.webshop.entities.Address;
import info.novatec.webshop.entities.Bill;
import info.novatec.webshop.entities.PurchaseOrder;
import info.novatec.webshop.persistence.OrderManager;
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
public class OrderService implements OrderManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    @Override
    public PurchaseOrder getOrderById(Long id) {
        PurchaseOrder order = null;
        try {
            order = (PurchaseOrder) em.createNamedQuery("Orders.findOrdersByOrderID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return order;
    }

    @Override
    public List<PurchaseOrder> getOrderByAccount(Account account) {
        List<PurchaseOrder> accountOrders = null;
        try {
            accountOrders = (List<PurchaseOrder>) em.createNamedQuery("Orders.findOrdersByAccount").setParameter("account", account).getResultList();
        } catch (NoResultException exeption) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return accountOrders;
    }

    @Override
    public boolean createOrder(PurchaseOrder order) {
        Bill bill = null;
        Address billingAddress = null;
        Address deliveryAddress = null;
        if (order.getBill().getId() != null) {
            bill = em.find(Bill.class, order.getBill().getId());
        }
        if (order.getDeliveryAddress().getId() != null) {
            deliveryAddress = em.find(Address.class, order.getDeliveryAddress().getId());
        }
        if (order.getBillingAddress().getId() != null) {
            billingAddress = em.find(Address.class, order.getBillingAddress().getId());
        }

        if (bill != null) {
            order.setBill(bill);
        }else{
            em.persist(order.getBill());
        }
        if (deliveryAddress != null) {
            order.setDeliveryAddress(deliveryAddress);
        }else{
            em.persist(order.getDeliveryAddress());
        }

        if (billingAddress != null) {
            order.setBillingAddress(billingAddress);
        }else{
            em.persist(order.getBillingAddress());
        }
        LOGGER.info("Address is: " + order.getBillingAddress().getStreet());
        em.persist(order);
        return em.contains(order);
    }

}
