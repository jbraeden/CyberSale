/*
 * Created by Patrick Abod on 2016.04.24  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.jsfclassespackage.util.Constants;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author patrickabod
 */
@Named(value = "searchManager")
@SessionScoped
public class SearchManager implements Serializable {
         
    private String category; 
    private String[] categories;
    private String query;
     
    @PostConstruct
    public void init() {
        categories  = Constants.ITEM_CATEGORY;
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
        
}
