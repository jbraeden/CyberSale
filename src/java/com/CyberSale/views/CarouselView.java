/*
 * Created by Joe Fletcher on 2016.04.10  * 
 * Copyright © 2016 Joe Fletcher. All rights reserved. * 
 */
package com.CyberSale.views;

import com.CyberSale.entitypackage.Item;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import java.util.Collections;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author cloud
 */
@ManagedBean
public class CarouselView implements Serializable {
    
    // List of Recently posted objects
    private List<Item> recent;
    // List of most popular objects based on their hit count
    private List<Item> popular;
    // List of the best deals avilable to the users of the app
    private List<Item> deals;
    // List of Related items to the current item being looked at
    private List<Item> related;
    // If the list of related is empty then this boolean is set to true
    private boolean emptyRelated = true;
    // The currently selected Item being looked at by the user
    private Item selectedItem;
    
    // EJB reference to the ItemFacade class
    @EJB
    ItemFacade itemFacade;
    
    // Init function called as soon as the class is instatiated
    @PostConstruct
    public void init() {
    }
 
    /**
     * Returns the List of most recent items
     * @return List of most recent items
     */
    public List<Item> getRecent() {
        
        // If the value of list has not been set (aka null) then fetch the most 
        // recent copy of it from the Session Map 
        if (recent == null)
            getFromSessionMap();
        
        return recent;
    }
    
    /**
     * Returns the List of popular items
     * @return List of popular items
     */
    public List<Item> getPopular() {
        // If the value of list has not been set (aka null) then fetch the most 
        // recent copy of it from the Session Map 
        if (popular == null)
            getFromSessionMap();
        
        return popular;
    }
    
    /**
     * Returns the list of the best Deals
     * @return  List of best deals based on price
     */ 
    public List<Item> getDeals() {
        // If the value of list has not been set (aka null) then fetch the most 
        // recent copy of it from the Session Map 
        if (deals == null)
            getFromSessionMap();
        
        return deals;
    }
 
    /**
     * Returns the Item object that is currently selected
     * @return Item field of selected item
     */
    public Item getSelectedItem() {
        return selectedItem;
    }
 
    /**
     * Sets the Item object that is currently selected
     * @param selectedItem the new value of Item to be set
     */
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * Sets the List of related items
     * @param related the new value for the List
     */
    public void setRelated(List<Item> related) {
        this.related = related;
    }

    /**
     * Returns the list of related item
     * @return List of related Item objects
     */
    public List<Item> getRelated() {
        return related;
    }

    /**
     * Returns the boolean if the Related items List is empty
     * @return true if the related items list is empty, false otherwise
     */
    public boolean isEmptyRelated() {
        return emptyRelated;
    }

    /**
     * Sets the value of the boolean representing if the related items list is
     * empty
     * @param emptyRelated the new value for the boolean
     */
    public void setEmptyRelated(boolean emptyRelated) {
        this.emptyRelated = emptyRelated;
    }
    
    /**
     * Uses the FacesContext abstract class to retrieve the session map for each
     * and every one of the global Lists we have in this class
     */
    private void getFromSessionMap() {
        recent = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("recent_items");
        
        popular = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("popular_items");
        
        deals = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("cheap_items");
    }
    
    /**
     * Returns List of relatedItems based on an ID given by the item to find
     * related items for
     * @param id the id of the Item in which we want to find the related items
     * for
     * @return the List of related Items 
     */
    public List<Item> relatedItems(Integer id) {
        Item i = itemFacade.findItemById(id);
        related = itemFacade.findRelatedItems(
                i, Constants.SEARCH_CATEGORY.indexOf(i.getCategory()) - 1);
        if (related == null) {
            related = Collections.EMPTY_LIST;
            emptyRelated = true;
        } else {
            emptyRelated = false;
        }
        
        return related;
    }
}