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
import com.CyberSale.sessionbeanpackage.ItemPhotoFacade;
import java.io.IOException;
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
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    
    @EJB
    private ItemPhotoFacade itemPhotoFacade;
    
    /* ArrayLists to hold Recent/Popular Items */
    private List<Item> recentItems;
    private List<Item> popularItems;
    private List<Item> cheapItems;
    
    private List<Comparison> comparisons; 
    
    @PostConstruct
    public void init() {       
        this.name = "";
        this.cost = 0;
        this.description = "";
    }

    /*
        Public Methods
    */    
    
    public void itemSelected(int itemId) {
        Item selectedItem = itemFacade.findItemById(itemId);
        
        if (selectedItem != null) {
            id = itemId;
            name = selectedItem.getItemName();
            description = selectedItem.getDescription();
            cost = selectedItem.getCost();
            productCode = selectedItem.getProductCodeValue();
            comparisons = fetchComparisons();
            selectedItem.setHits(selectedItem.getHits()+1);
            itemFacade.edit(selectedItem);
            
            if (itemPhotoFacade.findPhotosForItem(itemId) == null) {
                photos = new Photo[1];
                photos[0] = new Photo();
                photos[0].setFileName("default_photo.png");
            }
            else
                photos = itemPhotoFacade.findPhotosForItem(itemId).toArray(new Photo[0]);            
        }
    }
    
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
    
    public List<Comparison> getComparisons() {
        return comparisons;
    }
    
    public List<Comparison> fetchComparisons() {
        List<Comparison> list = new ArrayList<>();
        String itemId;
        if (productCode == null || productCode.isEmpty()) {
            itemId = name.replace(' ', '+');
        } else {            
            itemId = productCode;
        }
        String url = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=";

        url += itemId;
        Document doc; 
        try {
                doc = Jsoup.connect(url).get();
        } catch (IOException e) {
                // Invalid URL 
                e.printStackTrace();
                return list;
        }

        Elements elements = doc.getElementsByClass("s-result-item");

        // Iterate over all results 
        for (Element elm : elements) {
                String title = elm.getElementsByClass("s-access-title").html();
                Elements link = elm.getElementsByClass("s-access-detail-page");
                Elements prices = elm.getElementsByClass("a-color-price");

                String priceComp = prices.size() > 0 ? prices.first().html() : "No price found";
                String ref = link.size() > 0 ? link.first().attr("href") : "No link found";

                // Add new Comparison to list 
                list.add(new Comparison(ref, priceComp, title));
        }
        return list;
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
                getSessionMap().get("customer_id"));
                item.setZipcode(customer.getZipcode());
                
                getFacade().create(item);
                
                mapItem(item);
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
    
    public class Comparison {
        private final String link;
        private final String price; 
        private final String title; 
        
        public String getLink() {
            return link;
        }
        
        public String getPrice() {
            return price; 
        }
    
        public String getTitle() {
            return title; 
        }
        
        public Comparison(String link, String price, String title) {
            this.link = link;
            this.price = price;
            this.title = title; 
        }
    }        
    public void mapItem(Item i) {
        FacesContext.getCurrentInstance().getExternalContext().
              getSessionMap().put("item_id", i.getId());
    }
    
    public String done() {
        return "ItemDetail?faces-redirect=true";
    }
            
}
