/*
 * Created by Joseph Sebastian on 2016.04.05  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Item;
import com.CyberSale.jsfclassespackage.util.ItemCategory;
import com.CyberSale.jsfclassespackage.util.ProductCodeType;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Braeden
 * @author Patrick
 */
@Named(value = "itemManager")
@SessionScoped
public class ItemManager implements Serializable
{
    private String name;
    private String productCodeValue;
    private ProductCodeType[] productCodeTypes;
    private ItemCategory[] categories;
    private double cost;
    private String description;
    private Date postedDate;
    private boolean sold;
    private int hits;
    
    @EJB
    private ItemFacade itemFacade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductCodeValue() {
        return productCodeValue;
    }

    public void setProductCodeValue(String productCodeValue) {
        this.productCodeValue = productCodeValue;
    }

    public ProductCodeType[] getProductCodeTypes() {
        if (productCodeTypes == null) {
            setProductCodeTypes(ProductCodeType.values());
        }
        return productCodeTypes;
    }

    public void setProductCodeTypes(ProductCodeType[] productCodeTypes) {
        this.productCodeTypes = productCodeTypes;
    }

    public ItemCategory[] getCategories() {
        if (categories == null) {
            setCategories(ItemCategory.values());
        }
        return categories;
    }

    public void setCategories(ItemCategory[] categories) {
        this.categories = categories;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
    
    private ItemFacade getFacade() {
        return itemFacade;
    }
    
    /*
        CRUD operations for Item
    */
    
    public Item create(Item i) {
        try {
            getFacade().create(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Item read(Item i) {
        try {
            getFacade().find(i.getId());
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Item update(Item i) {
        try {
            getFacade().edit(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Item delete(Item i) {
        try {
            getFacade().remove(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    
            
}
