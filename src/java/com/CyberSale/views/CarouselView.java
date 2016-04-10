/*
 * Created by Joe Fletcher on 2016.04.10  * 
 * Copyright © 2016 Joe Fletcher. All rights reserved. * 
 */
package com.CyberSale.views;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author cloud
 */
@ManagedBean
public class CarouselView implements Serializable {
     
    private List<TempItem> items;
    private TempItem selectedItem;
     
     
    @PostConstruct
    public void init() {
       
        
        items = createItems(9);
        
    }
 
    public List<TempItem> getItems() {
        return items;
    }

 
    public TempItem getSelectedItem() {
        return selectedItem;
    }
 
    public void setSelectedItem(TempItem selectedItem) {
        this.selectedItem = selectedItem;
    }
    
     
    public List<TempItem> createItems(int size) {
        List<TempItem> list = new ArrayList<>();
        
        list.add(new TempItem("Apple TV", "Description...", "appletv", getRandomPrice()));
        list.add(new TempItem("Nike Bag", "Description...", "bag", getRandomPrice()));
        list.add(new TempItem("Chevy Cruze 2015", "Description...", "car", getRandomPrice()));
        list.add(new TempItem("Brown Couch", "Description...", "couch", getRandomPrice()));
        list.add(new TempItem("Like-new Desk", "Description...", "desk", getRandomPrice()));
        list.add(new TempItem("Burton Snowboard package", "Description...", "snowboard", getRandomPrice()));
        list.add(new TempItem("Dinner Table", "Description...", "table", getRandomPrice()));
        list.add(new TempItem("50\" LCD TV", "Description...", "tv", getRandomPrice()));
        list.add(new TempItem("Xbox One w/ Controller", "Description...", "xbox", getRandomPrice()));
        
         
        return list;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
    public int getRandomPrice() {
        return (int) (Math.random() * 10000);
    }
     
    public boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true: false;
    }
    
    public class TempItem
    {
        private String name;
        private String description;
        private String image;
        private int price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            
            String ans = NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(this.price);
            
            return ans;
        }

        public void setPrice(int price) {
            this.price = price;
        }
        
        
        
        public TempItem(String name, String description, String image, int price)
        {
            this.name = name;
            this.description = description;
            this.image = image;
            this.price = price;
        }
    }
}