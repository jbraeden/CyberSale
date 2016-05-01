/*
 * Created by CyberSale on 2016.04.19  * 
 * Copyright © 2016 CyberSale. All rights reserved. * 
 */
package com.CyberSale.entitypackage;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class that encapsulates the Object Relational Mapping (ORM) of the
 * 'Item' table in the relational database 'CyberSaleDB'.
 * 
 * The class also specifies Named Queries (predefined SQL queries) to execute
 * on CyberSaleDB, using the '@NamedQuery' annotation.
 * 
 * Logically, Item represents all data directly associated with an Item that has
 * been posted on the website (such as the item's name, description, cost, date 
 * that it was posted, etc).
 * 
 * @author Ryan Asper
 * @author patrickabod
 */
@Entity
@Table(name = "Item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id AND i.sold = false"),
    @NamedQuery(name = "Item.findByItemName", query = "SELECT i FROM Item i WHERE i.itemName = :itemName AND i.sold = false"),
    @NamedQuery(name = "Item.findByProductCodeType", query = "SELECT i FROM Item i WHERE i.productCodeType = :productCodeType AND i.sold = false"),
    @NamedQuery(name = "Item.findByProducrCodeValue", query = "SELECT i FROM Item i WHERE i.producrCodeValue = :producrCodeValue AND i.sold = false"),
    @NamedQuery(name = "Item.findByCategory", query = "SELECT i FROM Item i WHERE i.category = :category AND i.sold = false"),
    @NamedQuery(name = "Item.findByCost", query = "SELECT i FROM Item i WHERE i.cost = :cost AND i.sold = false"),
    @NamedQuery(name = "Item.findByPostedDate", query = "SELECT i FROM Item i WHERE i.postedDate = :postedDate AND i.sold = false"),
    @NamedQuery(name = "Item.findBySold", query = "SELECT i FROM Item i WHERE i.sold = :sold"),
    @NamedQuery(name = "Item.findByHits", query = "SELECT i FROM Item i WHERE i.hits = :hits AND i.sold = false"),
    @NamedQuery(name = "Item.findByZipcode", query = "SELECT i FROM Item i WHERE i.zipcode = :zipcode AND i.sold = false"),
    @NamedQuery(name = "Item.findItemsByName", query = "SELECT i FROM Item i WHERE i.itemName LIKE :pattern AND i.sold = false"),
    @NamedQuery(name = "Item.findItemsByNameAndCategory", query = "SELECT i FROM Item i WHERE i.itemName LIKE :pattern AND i.category = :category AND i.sold = false"),
})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    /* Private variables that map to columns in the 'Item' table */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "item_name")
    private String itemName;
    @Size(max = 4)
    @Column(name = "product_code_type")
    private String productCodeType;
    @Size(max = 256)
    @Column(name = "product_code_value")
    private String producrCodeValue;
    @Size(max = 19)
    @Column(name = "category")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost")
    private double cost;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "posted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sold")
    private boolean sold;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hits")
    private int hits;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zipcode")
    private int zipcode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private Collection<CustomerItem> customerItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private Collection<ItemPhoto> itemPhotoCollection;

    
    /**
     * Default constructor
     * 
     * Initializes a new instance of the Item class
     */
    public Item() {
        // Required empty constructor
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the Item class with the given 
     * Item id (unique, private integer key that specifies a record in the DB 
     * table).
     * 
     * @param id The unique id that specifies an individual Item record
     */
    public Item(Integer id) {
        this.id = id;
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the Item class with the given Item
     * attributes.
     * 
     * @param id The unique id that specifies an individual Item record
     * @param itemName The name of the item
     * @param cost The cost of the item (in US dollars)
     * @param description A string description of the item
     * @param postedDate The date that this item was first posted
     * @param sold Whether or not this item has been sold (to omit from searches)
     * @param hits The number of views this Item has had on the website
     * @param zipcode The zipcode in which this item is being sold in
     */
    public Item(Integer id, String itemName, float cost, String description, Date postedDate, boolean sold, int hits, int zipcode) {
        this.id = id;
        this.itemName = itemName;
        this.cost = cost;
        this.description = description;
        this.postedDate = postedDate;
        this.sold = sold;
        this.hits = hits;
        this.zipcode = zipcode;
    }

    
    
    /////////////// Getters & Setters //////////////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getProductCodeType() {
        return productCodeType;
    }

    public void setProductCodeType(String productCodeType) {
        this.productCodeType = productCodeType;
    }

    public String getProductCodeValue() {
        return producrCodeValue;
    }

    public void setProductCodeValue(String producrCodeValue) {
        this.producrCodeValue = producrCodeValue;
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

    public boolean getSold() {
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

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @XmlTransient
    public Collection<CustomerItem> getCustomerItemCollection() {
        return customerItemCollection;
    }

    public void setCustomerItemCollection(Collection<CustomerItem> customerItemCollection) {
        this.customerItemCollection = customerItemCollection;
    }

    @XmlTransient
    public Collection<ItemPhoto> getItemPhotoCollection() {
        return itemPhotoCollection;
    }

    public void setItemPhotoCollection(Collection<ItemPhoto> itemPhotoCollection) {
        this.itemPhotoCollection = itemPhotoCollection;
    }

    
    
    //////////////////// Overridden Methods ////////////////////
    /**
     * Overridden Method
     * 
     * Computes a hash for this Item object, using its unique id, to be used
     * when indexing (i.e., in a hash table).
     * 
     * @return The computed hash value.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Overridden Method
     * 
     * Checks whether this Item object is equal to the given Object. They
     * are considered equal if both objects:
     * - Are instances of the Item class
     * - Have equal id variables
     * 
     * @param object The Object being compared to this one
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Overridden Method
     * 
     * Returns a string representation of this object that is essentially the
     * package name and the id of the object.
     * 
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "com.CyberSale.entitypackage.Item[ id=" + id + " ]";
    }
    
}
