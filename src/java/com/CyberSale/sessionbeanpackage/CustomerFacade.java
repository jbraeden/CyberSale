/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    /*
        The following code is added to the generated code
    */
    
    public Customer findCustomerByUsername(String username) {
        try {
            if (em.createNamedQuery("Customer.findByUsername", Customer.class)
                    .setParameter("username", username)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Customer.findByUsername", Customer.class)
                    .setParameter("username", username).getResultList().get(0);
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public Customer findCustomerById(Integer id) {
        try {
            if (em.createNamedQuery("Customer.findById", Customer.class)
                    .setParameter("id", id)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Customer.findById", Customer.class)
                    .setParameter("id", id).getResultList().get(0);
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public Customer findCustomerByEmail(String email) {
        try {
            if (em.createNamedQuery("Customer.findByEmail", Customer.class)
                    .setParameter("email", email)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Customer.findByEmail", Customer.class)
                    .setParameter("email", email).getResultList().get(0);
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public List<Customer> findByCustomersZipcode(int zipcode) {
        try {
            if (em.createNamedQuery("Customer.findByZipcode", Customer.class)
                    .setParameter("zipcode", zipcode)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Customer.findByZipcode", Customer.class)
                    .setParameter("zipcode", zipcode).getResultList();
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    
    
}
