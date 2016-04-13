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
     
    private List<TempItem> recent;
    private List<TempItem> popular;
    private List<TempItem> deals;
    private TempItem selectedItem;
     
     
    @PostConstruct
    public void init() {
       
        
        recent = createItems(1);
        
        popular = createItems(2);
        
        deals = createItems(3);
        
    }
 
    public List<TempItem> getRecent() {
        return recent;
    }
    
    public List<TempItem> getPopular() {
        return popular;
    }
    
    public List<TempItem> getDeals() {
        return deals;
    }

 
    public TempItem getSelectedItem() {
        return selectedItem;
    }
 
    public void setSelectedItem(TempItem selectedItem) {
        this.selectedItem = selectedItem;
    }
    
     
    public List<TempItem> createItems(int id) {
        List<TempItem> list = new ArrayList<>();
        
        if(id == 1)
        {
            list.add(new TempItem("Mac Mini", "4 GB RAM ‑ 500 GB HDD ‑ 1.4 GHz Core", "appletv", 200));
            list.add(new TempItem("Nike Bag", "Brand New w/ Tag", "bag", 75));
            list.add(new TempItem("Chevy Cruze 2015", "10k miles", "car", 18000));
            list.add(new TempItem("Brown Couch", "small wear and tear", "couch", 100));
            list.add(new TempItem("Computer Desk", "fits in most standard college dorms", "desk", 90));
            list.add(new TempItem("Burton Snowboard package", "Bindings, Board, and bag all included", "snowboard", 120));
            list.add(new TempItem("Dinner Table", "Table and four chairs", "table", 50));
            list.add(new TempItem("50\" LCD TV", "Samsung. 3 HDMI ports. Wi-Fi", "tv", 499));
            list.add(new TempItem("Xbox One w/ Controller", "Just the console and one controller", "xbox", 350));
        
        }
        else if(id == 2)
        {
            list.add(new TempItem("Brown Couch", "small wear and tear", "couch", 100));
            list.add(new TempItem("Computer Desk", "fits in most standard college dorms", "desk", 90));
            list.add(new TempItem("Burton Snowboard package", "Bindings, Board, and bag all included", "snowboard", 120));
            list.add(new TempItem("Dinner Table", "Table and four chairs", "table", 50));
            list.add(new TempItem("50\" LCD TV", "Samsung. 3 HDMI ports. Wi-Fi", "tv", 499));
            list.add(new TempItem("Xbox One w/ Controller", "Just the console and one controller", "xbox", 350));
            list.add(new TempItem("Mac Mini", "4 GB RAM ‑ 500 GB HDD ‑ 1.4 GHz Core", "appletv", 200));
            list.add(new TempItem("Nike Bag", "Brand New w/ Tag", "bag", 75));
            list.add(new TempItem("Chevy Cruze 2015", "10k miles", "car", 18000));
            
        }
        else
        {
            list.add(new TempItem("Dinner Table", "Table and four chairs", "table", 50));
            list.add(new TempItem("50\" LCD TV", "Samsung. 3 HDMI ports. Wi-Fi", "tv", 499));
            list.add(new TempItem("Xbox One w/ Controller", "Just the console and one controller", "xbox", 350));
            list.add(new TempItem("Mac Mini", "4 GB RAM ‑ 500 GB HDD ‑ 1.4 GHz Core", "appletv", 200));
            list.add(new TempItem("Nike Bag", "Brand New w/ Tag", "bag", 75));
            list.add(new TempItem("Chevy Cruze 2015", "10k miles", "car", 18000)); 
            list.add(new TempItem("Brown Couch", "small wear and tear", "couch", 100));
            list.add(new TempItem("Computer Desk", "fits in most standard college dorms", "desk", 90));
            list.add(new TempItem("Burton Snowboard package", "Bindings, Board, and bag all included", "snowboard", 120));
            
        }
        
        
         
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