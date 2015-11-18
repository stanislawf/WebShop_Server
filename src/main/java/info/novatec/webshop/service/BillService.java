/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Bill;
import info.novatec.webshop.persistence.BillManager;
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
public class BillService implements BillManager {

    @PersistenceContext(unitName = "webshopPU")
    private EntityManager em;

    @Override
    public Bill getBillById(Long id) {
        Bill bill = null;
        try {
            bill = (Bill) em.createNamedQuery("Bill.findBillByBillID").setParameter("id", id).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(BillService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return bill;
    }

    @Override
    public Bill getBillByAccountOwner(String accountOwner) {
        Bill bill = null;
        try {
            bill = (Bill) em.createNamedQuery("Bill.findBillByAccountOwner").setParameter("accountOwner", accountOwner).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(BillService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return bill;
    }

    @Override
    public boolean doesBillExistInDB(Bill bill) {
        return em.contains(bill);
    }

    @Override
    public Bill getBillByAccountNumberAndOwner(Long accountNumber, String accountOwner) {
        Bill bill = null;
        try {
            bill = (Bill) em.createNamedQuery("Bill.findBillByAccountNumberAndOwner").setParameter("accountOwner", accountOwner).setParameter("accountNumber", accountNumber).getSingleResult();
        } catch (NoResultException exeption) {
            Logger.getLogger(BillService.class.getName()).log(Level.SEVERE, null, exeption);
        }
        return bill;
    }

    @Override
    public void updateBill(Bill bill) {
          Bill tempBill = em.find(Bill.class, bill.getId());
        if (tempBill != null) {
            tempBill = bill;
            em.merge(tempBill);
        }
    }
    
    
}
