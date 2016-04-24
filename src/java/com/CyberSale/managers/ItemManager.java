/*
 * Created by Joseph Sebastian on 2016.04.05  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.entitypackage.Item;
import com.CyberSale.entitypackage.Photo;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.CustomerFacade;
import com.CyberSale.sessionbeanpackage.CustomerItemFacade;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Braeden
 * @author Patrick
 */
@Named(value = "itemManager")
@SessionScoped
public class ItemManager implements Serializable {
    
    /* Item Fields */
    private int id;
    private String name;
    private Map<String, Object> productCodes;
    private String productCodeKey;
    private String productCode;
    private Map<String, Object> categories;
    private String category;
    private double cost;
    private String description;
    private Date postedDate;
    private boolean sold;
    private int hits;
    
    private Photo[] photos;
    
    private String statusMessage = "";
    
    @EJB
    private ItemFacade itemFacade;
    
    @EJB
    private CustomerFacade customerFacade;
    
    @EJB
    private CustomerItemFacade customerItemFacade;
    
    /* ArrayLists to hold Recent/Popular Items */
    private List<Item> recentItems;
    private List<Item> popularItems;
    private List<Item> cheapItems;
    
    @PostConstruct
    public void init() {       
        this.name = "Mac Mini";
        this.cost = 200.00;
        this.description = "4 GB RAM ‑ 500 GB HDD ‑ 1.4 GHz Core";
    }

    /*
        Public Methods
    */    
    
    public void OnLoad() {
        // Run Queries to Find Items
        recentItems = customerItemFacade.findRecentItems();
        popularItems = customerItemFacade.findPopularItems();
        cheapItems = customerItemFacade.findCheapItems();
        
        // Put Item Arrays into SessionMap        
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("recent_items", recentItems);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("popular_items", popularItems);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("cheap_items", cheapItems);
    }
    
    
    
    /*
        Setters & Getters
    */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getProductCodes() {
        if (productCodes == null) {
            productCodes = new LinkedHashMap<>();
            for (int i = 0; i < Constants.PRODUCT_CODE_TYPE.length; i++) {
                productCodes.put(Constants.PRODUCT_CODE_TYPE[i], i);
            }
        }
        return productCodes;
    }

    public void setProductCodes(Map<String, Object> productCodes) {
        this.productCodes = productCodes;
    }

    public String getProductCodeKey() {
        return productCodeKey;
    }

    public void setProductCodeKey(String productCodeKey) {
        this.productCodeKey = productCodeKey;
    }

    public Map<String, Object> getCategories() {
        if (categories == null) {
            categories = new LinkedHashMap<>();
            for (int i = 0; i < Constants.ITEM_CATEGORY.length; i++) {
                categories.put(Constants.ITEM_CATEGORY[i], i);
            }
        }
        return categories;
    }

    public void setCategories(Map<String, Object> categories) {
        this.categories = categories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Photo[] getPhotos() {
        return photos;
    }

    public void setPhotos(Photo[] photos) {
        this.photos = photos;
    }       

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    private ItemFacade getFacade() {
        return itemFacade;
    }
    
    public String createItem() {
        // create item

        if (statusMessage.isEmpty()) {
            try {
                Item item = new Item();
                item.setItemName(name);
                item.setCategory(category);                
                item.setCost(cost);
                item.setDescription(description);
                item.setHits(hits);
                item.setPostedDate(new Date());
                item.setProductCodeValue(productCode);
                item.setProductCodeType(productCodeKey);
                item.setSold(false);
                item.setItemPhotoCollection(null);
                
                Customer customer = customerFacade.findCustomerById((Integer)FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("user_id"));
                item.setZipcode(customer.getZipcode());
                
                getFacade().create(item);
            } catch (EJBException e) {
                System.out.println(e);
                statusMessage = "Something went wrong while creating your item!";
                return "";
            }
            return "/AddItemUploadImage.xhtml?faces-redirect=true";
        }
        return "";
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
    
    /*
        Additional Item Table queries
    */
    
    public Item getItemById(Integer id) {
        Item item = null;
        try {
            item = getFacade().findItemById(id);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
    
    public Item getItemByName(String name) {
        Item item = null;
        try {
            item = getFacade().findItemByName(name);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
    
    public List<Item> getItemsByName(String name) {
        List<Item> items = null;
        try {
            items = getFacade().findItemsByName(name);
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public List<Item> getItemsByCost(double costMin, double costMax) {
        List<Item> items = null;
        try {
            items = getFacade().findItemsByCost(costMin, costMax);
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public List<Item> getItemsByHits(int hits) {
        List<Item> items = null;
        try {
            items = getFacade().findItemsByHits(hits);
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    
            
}
