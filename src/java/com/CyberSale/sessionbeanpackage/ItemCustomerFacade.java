/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.entitypackage.Item;
import com.CyberSale.entitypackage.ItemCustomer;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 */
@Stateless
public class ItemCustomerFacade extends AbstractFacade<ItemCustomer> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemCustomerFacade() {
        super(ItemCustomer.class);
    }
    
    /*
        The following methods are added to the generated code
    */
    
    public List<Item> findItemsForCustomer(Integer customerId) {
        List<ItemCustomer> customerItems = null;
        try {
            customerItems = em.createNamedQuery("findItemCustomerByCustomer", ItemCustomer.class)
                    .setParameter("customerId", customerId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<Item> items = new ArrayList<>();
        if (customerItems != null && !customerItems.isEmpty()) {
            for (ItemCustomer ic : customerItems) {
                if ((ic.getItemId().getId()).equals(customerId)) {
                    try {
                        items.add(em.find(Item.class, ic.getItemId().getId()));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return items;
        }
        return null;
    }
    
    public Customer findCustomerForItem(Integer itemId) {
        ItemCustomer itemCustomer = null;
        try {
            itemCustomer = em.createNamedQuery("findItemCustomerByItem", ItemCustomer.class)
                    .setParameter("itemId", itemId)
                    .getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Customer customer = null;
        if (itemCustomer != null) {
            try {
                customer = em.find(Customer.class, itemCustomer.getCustomerId().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customer;
    }
    
}
