/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.service;

import info.novatec.webshop.entities.Article;
import info.novatec.webshop.persistence.WebshopManager;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author stanislawfreund
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class WebshopService implements WebshopManager {

 
 

}
