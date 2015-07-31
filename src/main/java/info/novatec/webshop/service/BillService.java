/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Bill;
import info.novatec.webshop.persistence.BillManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sf
 */
public class BillService implements BillManager{

      @PersistenceContext(unitName = "webshopPU")
  private EntityManager em;
    
    @Override
    public Bill getBillById(Long id) {
      return (Bill) em.createNamedQuery("Bill.findBillByID").setParameter("id", id).getSingleResult();
  }

    @Override
    public Bill getBillByAccountOwner(String accountOwner) {
        return (Bill) em.createNamedQuery("Bill.findBillByAccountOwner").setParameter("accountOwner", accountOwner).getSingleResult();
   }
    
}
