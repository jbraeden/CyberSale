/*
 * Created by Patrick Abod on 2016.04.24  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Item;
import com.CyberSale.entitypackage.Photo;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import com.CyberSale.sessionbeanpackage.ItemPhotoFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author patrickabod
 */
@Named(value = "searchManager")
@SessionScoped
public class SearchManager implements Serializable {
         
    @EJB
    ItemFacade itemFacade;
    
    @EJB
    ItemPhotoFacade itemPhotoFacade;
    
    private String category; 
    private String[] categories;
    private String query;
    private List<Item> results;
    private Item currentItem;
    private Photo currentPhoto;
     
    @PostConstruct
    public void init() {
        categories  = Constants.SEARCH_CATEGORY;
    }
 
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
 
    public void setCategories(String category) {
        this.category = category;
    }
 
    public String[] getCategories() {
        return categories;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Item> getResults() {
        return results;
    }

    public void setResults(List<Item> results) {
        this.results = results;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public Photo getCurrentPhoto() {
        currentPhoto = itemPhotoFacade.findPhotosForItem(currentItem.getId()).get(0);
        return currentPhoto;
    }

    public void setCurrentPhoto(Photo currentPhoto) {
        this.currentPhoto = currentPhoto;
    }
    
    public String search() {
        if (query.trim().isEmpty()) {
            return "";
        }
        try {
            results = itemFacade.findItemsByName(query);
            System.out.println(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Search.xhtml?faces-redirect=true";
    }
}
