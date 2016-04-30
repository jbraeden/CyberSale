/*
 * Created by Patrick Abod on 2016.04.12  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
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
 * @author Patrick Abod
 * @author Shawn Amjad
 * 
 * This class is used to handle all queries on the customer table
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    /* entity manager object with persistence context of a Cyber Sale entity */
    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    /**
     * get the entity manager object
     * @return the entity manger
     */
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
    
    /**
     * Find a customer by his or her username
     * @param username The username on which to search
     * @return the customer if found, null otherwise
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
    
    /**
     * Find a customer by his or her customer id
     * @param id the id on which to search
     * @return the found customer, null otherwise
     */
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
    
    /**
     * Find a customer by his or her email
     * @param email the email on which to search
     * @return the found customer, null otherwise
     */
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
    
    /**
     * Find a list of customers by zip code
     * @param zipcode the zip code on which to search
     * @return the list of customers found, null if empty
     */
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
