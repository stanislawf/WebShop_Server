/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.novatec.webshop.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author sf
 */


@Entity
public class Guest extends Account implements Serializable{
    private static final long serialVersionUID = 1L;
}
