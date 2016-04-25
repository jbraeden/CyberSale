/*
 * Created by Patrick Abod on 2016.04.19  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
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
 * 
 * @author patrickabod
 */
@Entity
@Table(name = "Item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
    @NamedQuery(name = "Item.findByItemName", query = "SELECT i FROM Item i WHERE i.itemName = :itemName"),
    @NamedQuery(name = "Item.findByProductCodeType", query = "SELECT i FROM Item i WHERE i.productCodeType = :productCodeType"),
    @NamedQuery(name = "Item.findByProducrCodeValue", query = "SELECT i FROM Item i WHERE i.producrCodeValue = :producrCodeValue"),
    @NamedQuery(name = "Item.findByCategory", query = "SELECT i FROM Item i WHERE i.category = :category"),
    @NamedQuery(name = "Item.findByCost", query = "SELECT i FROM Item i WHERE i.cost = :cost"),
    @NamedQuery(name = "Item.findByPostedDate", query = "SELECT i FROM Item i WHERE i.postedDate = :postedDate"),
    @NamedQuery(name = "Item.findBySold", query = "SELECT i FROM Item i WHERE i.sold = :sold"),
    @NamedQuery(name = "Item.findByHits", query = "SELECT i FROM Item i WHERE i.hits = :hits"),
    @NamedQuery(name = "Item.findByZipcode", query = "SELECT i FROM Item i WHERE i.zipcode = :zipcode"),
    @NamedQuery(name = "Item.findItemsByName", query = "SELECT i FROM Item i WHERE i.itemName LIKE :pattern"),
    @NamedQuery(name = "Item.findItemsByNameAndCategory", query = "SELECT i FROM Item i WHERE i.itemName LIKE :pattern AND i.category = :category"),
})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
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

    public Item() {
    }

    public Item(Integer id) {
        this.id = id;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.CyberSale.entitypackage.Item[ id=" + id + " ]";
    }
    
}
