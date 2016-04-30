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
 * @author Shawn Amjad
 */
@Named(value = "searchManager")
@SessionScoped
public class SearchManager implements Serializable {
    
    /* Java Bean that is initialized at runtime */
    @EJB
    ItemFacade itemFacade;
    
    /* Java Bean that is initialized at runtime */
    @EJB
    ItemPhotoFacade itemPhotoFacade;
    
    /* Search Manager fields */
    private String category; 
    private String[] categories;
    private String query = "";
    private List<Item> results;
    private Item currentItem;
    private String filePath;
     
    @PostConstruct
    public void init() {
        categories  = Constants.SEARCH;
    }
 
    /*
        Getters and Setters
    */
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

    public String getFilePath() {
        Photo photo = itemPhotoFacade.findPhotosForItem(currentItem.getId()).get(0);
        setFilePath("/ItemPhotos/" + currentItem.getId() + "/" + photo.getFileName());
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * This method is used to execute the search when the search icon is clicked
     * @return The web page redirect string to the search page
     */
    public String search() {
        // search on all items
        if (category.equals("All")) {
            try {
                results = itemFacade.findItemsByName(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // search on items within specified category
            try {
                results = itemFacade.findItemsByNameAndCategory(query, Constants.SEARCH_CATEGORY.indexOf(category) - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        query = "";
        return "Search.xhtml?faces-redirect=true";
    }
}
