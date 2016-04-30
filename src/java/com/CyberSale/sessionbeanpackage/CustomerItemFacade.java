/*
 * Created by Patrick Abod on 2016.04.19  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.entitypackage.CustomerItem;
import com.CyberSale.entitypackage.Item;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author patrickabod
 * This class is used to handle all queries in the customer-item
 * relational DB table
 */
@Stateless
public class CustomerItemFacade extends AbstractFacade<CustomerItem> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerItemFacade() {
        super(CustomerItem.class);
    }
    
    
    /*
        Methods added to generated code
    */
    
    /**
     * Find a the seller of an item
     * @param itemId the id of the item
     * @return the customer who is selling the item
     * Should never be null
     */
    public Customer findItemSeller(int itemId) {
        
        Customer seller = null;
        
        CustomerItem customerItem =  (CustomerItem) em.createQuery("SELECT ci FROM CustomerItem ci WHERE ci.itemId.id = :itemId")
                .setParameter("itemId", itemId)
                .getSingleResult();
        
        if (customerItem != null) {
            seller = em.find(Customer.class, customerItem.getCustomerId().getId());
        }
        
        return seller;
    }
    
    /**
     * Find all the recent items posted
     * @return a list of the recently posted items, null if there are no items
     */
    public List<Item> findRecentItems() {
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.sold = false ORDER BY i.postedDate DESC").getResultList(); 

        if (items.isEmpty())
            return null;
        else
            return items;
    }
    
    /**
     * Find all the popular items posted
     * @return a list of the popular items posted, null if there are no items
     */
    public List<Item> findPopularItems() {
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.sold = false ORDER BY i.hits DESC").getResultList(); 

        if (items.isEmpty())
            return null;
        else
            return items;
    }
    
    /**
     * Find all the recent items posted
     * @return a list of the recently posted items, null if there are no items
     */
    public List<Item> findCheapItems() {
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.sold = false ORDER BY i.cost ASC").getResultList(); 

        if (items.isEmpty())
            return null;
        else
            return items;
    }

    /**
     * Find all the items for a particular
     * @param customerId the customer id
     * @return the list of items belonging to the customer
     */
    public List<Item> findUserItems(int customerId) {
        List<CustomerItem> customerItemList = em.createQuery("SELECT ci FROM CustomerItem ci WHERE ci.customerId.id = :customerId")
                .setParameter("customerId", customerId)
                .getResultList();
        
        List<Item> ui = new ArrayList<Item>();
        
        for(CustomerItem ci : customerItemList) {
            ui.add(ci.getItemId());
        }
        
        if (ui.isEmpty())
            return null;
        else
            return ui;
    }
}
