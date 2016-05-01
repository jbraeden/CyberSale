/*
 * Created by CyberSale on 2016.04.19  * 
 * Copyright © 2016 CyberSale. All rights reserved. * 
 */
package com.CyberSale.entitypackage;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class that encapsulates the Object Relational Mapping (ORM) of the
 * 'ItemPhoto' table in the relational database 'CyberSaleDB'.
 * 
 * The class also specifies Named Queries (predefined SQL queries) to execute
 * on CyberSaleDB, using the '@NamedQuery' annotation.
 * 
 * Logically, ItemPhoto represents a relation between an Item that has been
 * posted to the website and a Photo that has been uploaded with that Item. The
 * two have a one-to-many relationship (one item may have multiple photos 
 * uploaded with it).
 * 
 * @author Ryan Asper
 * @author patrickabod
 */
@Entity
@Table(name = "ItemPhoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemPhoto.findAll", query = "SELECT i FROM ItemPhoto i"),
    @NamedQuery(name = "ItemPhoto.findById", query = "SELECT i FROM ItemPhoto i WHERE i.id = :id"),
    @NamedQuery(name = "ItemPhoto.findItemPhotoByItem", query = "SELECT i FROM ItemPhoto i WHERE i.itemId.id = :itemId")})
public class ItemPhoto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    /* Private variables that map to columns in the 'ItemPhoto' table */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "photo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Photo photoId;
    
    
    /**
     * Default constructor
     * 
     * Initializes a new instance of the ItemPhoto class
     */
    public ItemPhoto() {
        // Required empty constructor
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the ItemPhoto class with the given 
     * ItemPhoto id (unique, private integer key that specifies a record in the 
     * DB table).
     * 
     * @param id The unique id that specifies an individual ItemPhoto record
     */
    public ItemPhoto(Integer id) {
        this.id = id;
    }

    
    
    /////////////// Getters & Setters //////////////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Photo getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Photo photoId) {
        this.photoId = photoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    
    //////////////////// Overridden Methods ////////////////////
    /**
     * Overridden Method
     * 
     * Checks whether this ItemPhoto object is equal to the given Object. They
     * are considered equal if both objects:
     * - Are instances of the ItemPhoto class
     * - Have equal id variables
     * 
     * @param object The Object being compared to this one
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemPhoto)) {
            return false;
        }
        ItemPhoto other = (ItemPhoto) object;
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
        return "com.CyberSale.entitypackage.ItemPhoto[ id=" + id + " ]";
    }
    
}
