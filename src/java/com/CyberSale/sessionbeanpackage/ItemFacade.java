/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 */
@Stateless
public class ItemFacade extends AbstractFacade<Item> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemFacade() {
        super(Item.class);
    }
    
    /*
        The following methods are added to the generated code
    */
    
    public Item findItemById(Integer id) {
        try {
            if (em.createNamedQuery("Item.findById", Item.class)
                    .setParameter("id", id)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Item.findById", Item.class)
                    .setParameter("id", id).getResultList().get(0);
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public Item findItemByName(String name) {
        try {
            if (em.createNamedQuery("Item.findByItemName", Item.class)
                    .setParameter("itemName", name)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Item.findByItemName", Item.class)
                    .setParameter("itemName", name).getResultList().get(0);
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public List<Item> findItemsByName(String namePattern) {
        try {
            if (em.createNamedQuery("Item.findItemsByName", Item.class)
                    .setParameter("pattern", "%" + namePattern + "%")
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Item.findItemsByName", Item.class)
                    .setParameter("pattern", "%" + namePattern + "%").getResultList();
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public List<Item> findItemsByNameAndCategory(String namePattern, int category) {
        try {
            if (em.createNamedQuery("Item.findItemsByNameAndCategory", Item.class)
                    .setParameter("pattern", "%" + namePattern + "%")
                    .setParameter("category", String.valueOf(category))
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Item.findItemsByNameAndCategory", Item.class)
                    .setParameter("pattern", "%" + namePattern + "%")
                    .setParameter("category", String.valueOf(category))
                    .getResultList();
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public List<Item> findRelatedItems(Item item, int category) {
        try {
            if (em.createNamedQuery("Item.findItemsByNameAndCategory", Item.class)
                    .setParameter("pattern", "%" + item.getItemName() + "%")
                    .setParameter("category", String.valueOf(category))
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                List<Item> results = em.createNamedQuery("Item.findItemsByNameAndCategory", Item.class)
                    .setParameter("pattern", "%" + item.getItemName() + "%")
                    .setParameter("category", String.valueOf(category))
                    .getResultList();
                ArrayList<Item> toReturn = new ArrayList<>();
                for (Item i : results) {
                    if (!i.getId().equals(item.getId())) {
                        toReturn.add(i);
                    }
                }
                return toReturn;
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public List<Item> findItemsByCost(double costMin, double costMax) {
        try {
            if (em.createNamedQuery("Item.findItemsByCost", Item.class)
                    .setParameter("costMin", costMin)
                    .setParameter("costMax", costMax)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Item.findItemsByCost", Item.class)
                    .setParameter("costMin", costMin)
                    .setParameter("costMax", costMax)
                    .getResultList();
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    public List<Item> findItemsByHits(int hits) {
        try {
            if (em.createNamedQuery("Item.findItemsByHits",Item.class)
                    .setParameter("hits", hits)
                    .setMaxResults(9)
                    .getResultList().isEmpty()) {
                return null;
            }
            else {
                 return em.createNamedQuery("Item.findItemsByHits",Item.class)
                    .setParameter("hits", hits)
                    .setMaxResults(9)
                    .getResultList();
                            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
}
