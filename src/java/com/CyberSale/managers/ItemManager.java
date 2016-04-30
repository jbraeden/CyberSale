/*
 * Created by Joseph Sebastian on 2016.04.05  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.entitypackage.CustomerItem;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author Braeden
 * @author Patrick Abod
 * @author Shawn Amjad
 * 
 * This bean class is used to handle all interactions that involve
 * items, including creating an item, viewing an item, linking customers
 * to items, linking items to photos, and performing price comparisons
 * on items.
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
    
    /* Used to hold an items photos */
    private List<Photo> photos;
    
    /* Status message to hold any validation errors to display to the user */
    private String statusMessage = "";
    
    /* Java Bean that is initialized at runtime */
    @EJB
    private ItemFacade itemFacade;
    
    /* Java Bean that is initialized at runtime */
    @EJB
    private CustomerFacade customerFacade;
    
    /* Java Bean that is initialized at runtime */
    @EJB
    private CustomerItemFacade customerItemFacade;
    
    /* Java Bean that is initialized at runtime */
    @EJB
    private ItemPhotoFacade itemPhotoFacade;
    
    /* Lists to hold Recent/Popular/cheap Items */
    private List<Item> recentItems;
    private List<Item> popularItems;
    private List<Item> cheapItems;
    
    /* List to hold item comparisons */
    private List<Comparison> comparisons; 
    
    @PostConstruct
    public void init() {       
        this.name = "";
        this.cost = 0;
        this.description = "";
    }    
    
    /**
     * This method is called to selected a specific item
     * @param itemId the id of the item to select
     */
    public void itemSelected(int itemId) {
        // find the item in the DB
        Item selectedItem = itemFacade.findItemById(itemId);
        
        if (selectedItem != null) {
            // set the fields for the seleced item
            id = itemId;
            name = selectedItem.getItemName();
            description = selectedItem.getDescription();
            cost = selectedItem.getCost();
            productCode = selectedItem.getProductCodeValue();
            comparisons = fetchComparisons();
            // increase the hit count of the selected item
            selectedItem.setHits(selectedItem.getHits()+1);
            itemFacade.edit(selectedItem);
            photos = itemPhotoFacade.findPhotosForItem(itemId);
        }
    }
    
    /**
     * This method is called to update the recently posted items,
     * the popular items, and the cheap items when the home
     * page is loaded.
     */
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
        // map the product codes if they are not already mapped
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
        // map the item categories if they are not already mapped
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
    
    /**
     * This method is used to obtain the price comparisons
     * for the selected object.
     * Scrapes Amazon
     * @return The list of comparison objects
     */
    public List<Comparison> fetchComparisons() {
        List<Comparison> list = new ArrayList<>();
        String nameUrl = name.replace(' ', '+');
        String itemId;
        itemId = (productCode == null || productCode.isEmpty()) ?
                nameUrl : productCode;
 
        Elements elements = scrapePrices(itemId);
        if (elements.size() == 0 && itemId.equals(productCode)) {
            elements = scrapePrices(nameUrl);
        }
        
        // Iterate over all results 
        for (Element elm : elements) {
                String title = elm.getElementsByClass("s-access-title").html();
                Elements link = elm.getElementsByClass("s-access-detail-page");
                Elements prices = elm.getElementsByClass("a-color-price");
                Elements imageLinks = elm.getElementsByClass("s-access-image"); 

                String priceComp = prices.size() > 0 ? prices.first().html() : "No price found";
                String ref = link.size() > 0 ? link.first().attr("href") : "No link found";
                String imageLink = imageLinks.size() > 0 ? 
                        imageLinks.first().attr("src") : 
                        "http://www.turnerduckworth.com/media/filer_public/b4/ac/b4ac5dfe-b335-403c-83b2-ec69e01f94e6/td-amazon-hero.svg";

                // Add new Comparison to list 
                list.add(new Comparison(ref, priceComp, title, imageLink));
        }
        return list;
    }

    /**
     * This method scrapes the prices for a given item using
     * an Amazon api call
     * @param itemId the item id upon which the query
     * @return the elements of the scraping process
     */
    private Elements scrapePrices(String itemId) {
        String url = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=";
        url += itemId;
        
        try {
            Document results = Jsoup.connect(url).timeout(6000).get();
            return results.getElementsByClass("s-result-item");
        } catch (IOException e) {
            // Invalid URL 
            e.printStackTrace();
        }
        return new Elements();
    }
    
    /**
     * This method is used to get the paths of all the photos
     * kept on the server's file system
     * @return The list of photo paths
     */
    public List<String> getPhotosPaths() {
        // return the default image if no photos exist
        if (photos == null || photos.isEmpty()) {
            List<String> photoPaths = new ArrayList<>();
            photoPaths.add("/resources/images/default_item_photo.png");
            return photoPaths;
        }
        // return the photo file paths
        else {
            List<Photo> results = itemPhotoFacade.findPhotosForItem(this.id);
            ArrayList<String> photoPaths = new ArrayList<>();
            for (Photo photoObject : results) {
                photoPaths.add("/ItemPhotos/" + this.id + "/" + photoObject.getFileName());
            }
            return photoPaths;
        }
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
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
    
    /**
     * This method is used to create a new item and add it to
     * the Cyber Sale DB
     * @return the image upload web page redirect String
     */
    public String createItem() {
        // create item

        if (statusMessage.isEmpty()) {
            try {
                // set the item deafult fields and input information
                Item item = new Item();
                item.setItemName(name);
                item.setCategory(category);                
                item.setCost(cost);
                item.setDescription(description);
                item.setHits(0);
                item.setPostedDate(new Date());
                item.setProductCodeValue(productCode);
                item.setProductCodeType(productCodeKey);
                item.setSold(false);
                item.setItemPhotoCollection(null);
                
                // Set the item's zipcode to the customer's
                Customer customer = customerFacade.findCustomerById((Integer)FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("customer_id"));
                item.setZipcode(customer.getZipcode());
                
                // persist the item to the DB
                getFacade().create(item);
                
                // map the customer to the item using the relational DB
                CustomerItem customerItem = new CustomerItem();
                customerItem.setCustomerId(customer);
                customerItem.setItemId(item);
                customerItemFacade.create(customerItem);

                // map the newly created item
                mapItem(item);
            } catch (EJBException e) {
                System.out.println(e);
                statusMessage = "Something went wrong while creating your item!";
                return "";
            }
            
            return "AddItemUploadImage?faces-redirect=true";
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
    
    /**
     * Used to find a particular item by its id
     * @param id the id of the item to find
     * @return the found item
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
    
    /**
     * Used to find a particular item by its name
     * @param name the name of the item to find
     * @return the found item
     */
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
    
    /**
     * Used to find a list of item using a name pattern
     * @param name the name pattern on which to search
     * @return the list of items found
     */
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
    
    /**
     * Used to find items within a particular cost range
     * @param costMin the minimum cost
     * @param costMax the maximum cost
     * @return the list of items within the range
     */
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
    
    /**
     * Used to find items by the number of hits they have
     * @param hits the number of hits on which to search
     * @return the found items
     */
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
    
    /**
     * inner comparison class used for Amazon searching
     */
    public class Comparison {
        /* Comparison fields */
        private final String link;
        private final String price; 
        private final String title; 
        private final String imageLink;
        
        public String getImageLink() {
            return imageLink;
        }
        
        public String getLink() {
            return link;
        }
        
        public String getPrice() {
            return price; 
        }
    
        public String getTitle() {
            return title; 
        }
        
        // Constructor or the comparison object
        public Comparison(String link, String price, String title, String imageLink) {
            this.link = link;
            this.price = price;
            this.title = title; 
            this.imageLink = imageLink;
        }
    }    
    
    /**
     * Used to map an item during a user's session
     * @param i the item to map
     */
    public void mapItem(Item i) {
        FacesContext.getCurrentInstance().getExternalContext().
              getSessionMap().put("item_id", i.getId());
    }
    
    /**
     * Used to redirect the user when they are done uploading files
     * @return the home web page redirect string
     */
    public String done() {
        return "index?faces-redirect=true";
    }
    
    /**
     * Used to mark a particular item as being sold
     * @param itemId the id of the item to mark
     * @return the customer's my item page redirect string
     */
    public String markAsSold(int itemId) {
        Item item = itemFacade.findItemById(itemId);
        item.setSold(true);
        itemFacade.edit(item);
        return "UserItems?faces-redirect=true";
    }
      
    /**
     * helper method to clear the item fields
     */
    public void clear() {
        this.name = "";
        this.cost = 0;
        this.description = "";
        this.productCode = "";
    }
    
    /**
     * get a particular image's photo path
     * @param photo the photo object
     * @return the photo path String
     */
    public String fetchPhotoPath(Photo photo) {
        List<Photo> results = itemPhotoFacade.findPhotosForItem(this.id);
        if (results == null || results.isEmpty()) {
            return "/resources/images/default_item_photo.png";
        }
        else {
            return "/ItemPhotos/" + this.id + "/" + photo.getFileName();
        }
        
    }
}
