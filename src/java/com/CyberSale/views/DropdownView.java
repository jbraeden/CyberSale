/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CyberSale.views;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
 
@ManagedBean
@ViewScoped
public class DropdownView implements Serializable {
     
    private String category; 
    private Map<String,String> categories;
     
    @PostConstruct
    public void init() {
        categories  = new LinkedHashMap<String, String>();
        categories.put("All", "All");
        categories.put("Appliances", "Appliances");
        categories.put("Books", "Books");
        categories.put("Electronics", "Electronics");
        categories.put("Toys", "Toys");
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
 
    public Map<String, String> getCategories() {
        return categories;
    }
}
