/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.persistence;

import info.novatec.webshop.entities.OrderLine;

/**
 *
 * @author sf
 */
public interface OrderLineManager {
       public OrderLine getOrderLineById(Long id);
}
