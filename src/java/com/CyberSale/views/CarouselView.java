/*
 * Created by Joe Fletcher on 2016.04.10  * 
 * Copyright © 2016 Joe Fletcher. All rights reserved. * 
 */
package com.CyberSale.views;

import com.CyberSale.entitypackage.Item;
import com.CyberSale.entitypackage.Photo;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author cloud
 */
@ManagedBean
public class CarouselView implements Serializable {
    
    private List<Item> recent;
    private List<Item> popular;
    private List<Item> deals;
    private List<Item> related;
    private boolean emptyRelated = true;
    private Item selectedItem;
    
    @EJB
    ItemFacade itemFacade;
    
    
    @PostConstruct
    public void init() {
    }
 
    public List<Item> getRecent() {
        if (recent == null)
            getFromSessionMap();
        
        return recent;
    }
    
    public List<Item> getPopular() {
        if (popular == null)
            getFromSessionMap();
        
        return popular;
    }
    
    public List<Item> getDeals() {
        if (deals == null)
            getFromSessionMap();
        
        return deals;
    }
 
    public Item getSelectedItem() {
        return selectedItem;
    }
 
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setRelated(List<Item> related) {
        this.related = related;
    }

    public List<Item> getRelated() {
        return related;
    }

    public boolean isEmptyRelated() {
        return emptyRelated;
    }

    public void setEmptyRelated(boolean emptyRelated) {
        this.emptyRelated = emptyRelated;
    }
    
    
    private void getFromSessionMap() {
        recent = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("recent_items");
        
        popular = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("popular_items");
        
        deals = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("cheap_items");
    }
    
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