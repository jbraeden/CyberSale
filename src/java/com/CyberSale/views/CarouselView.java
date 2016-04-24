/*
 * Created by Joe Fletcher on 2016.04.10  * 
 * Copyright © 2016 Joe Fletcher. All rights reserved. * 
 */
package com.CyberSale.views;

import com.CyberSale.entitypackage.Item;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
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
    private Item selectedItem;
    
    
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
    
    
    private void getFromSessionMap() {
        recent = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("recent_items");
        
        popular = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("popular_items");
        
        deals = (List<Item>) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("cheap_items");
    }
}