/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.OrderLine;
import info.novatec.webshop.persistence.OrderLineManager;
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
public class OrderLineService implements OrderLineManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;

    @Override
    public OrderLine getOrderLineById(Long id) {
        OrderLine orderLine = null;
        try {
            orderLine = (OrderLine) em.createNamedQuery("Orderline.findOrderlineByOrderLineID").setParameter("id", id).getSingleResult();

        }catch (NoResultException exeption) {
            Logger.getLogger(OrderLineService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return orderLine;
    }
    
    @Override
    public void createOrderLine(OrderLine orderL){
        em.persist(orderL);
    }

}
