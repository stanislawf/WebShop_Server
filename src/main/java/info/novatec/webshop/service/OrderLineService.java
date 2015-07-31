/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.OrderLine;
import info.novatec.webshop.persistence.OrderLineManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sf
 */
public class OrderLineService implements OrderLineManager{
     @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;
    
  

    @Override
    public OrderLine getOrderLineById(Long id) {
          return (OrderLine) em.createNamedQuery("Orderline.findOrderlineByID").setParameter("id", id).getSingleResult();
   }

}
