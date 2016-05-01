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
 * 'CustomerItem' table in the relational database 'CyberSaleDB'.
 * 
 * The class also specifies Named Queries (predefined SQL queries) to execute
 * on CyberSaleDB, using the '@NamedQuery' annotation.
 * 
 * Logically, CustomerItem represents a relation between a Customer and an
 * Item that has been posted on the website. The two have a one-to-many relation
 * (one customer may have multiple items posted).
 * 
 * @author Ryan Asper
 * @author patrickabod
 */
@Entity
@Table(name = "CustomerItem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerItem.findAll", query = "SELECT c FROM CustomerItem c"),
    @NamedQuery(name = "CustomerItem.findById", query = "SELECT c FROM CustomerItem c WHERE c.id = :id")})
public class CustomerItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    /* Private variables that map to columns in the 'CustomerItem' table */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Customer customerId;

    
    /**
     * Default constructor
     * 
     * Initializes a new instance of the CustomerItem class
     */
    public CustomerItem() {
        // Required empty constructor
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the CustomerItem class with the given 
     * CustomerItem id (unique, private integer key that specifies a record in 
     * the DB table).
     * 
     * @param id The unique id that specifies an individual CustomerItem record
     */
    public CustomerItem(Integer id) {
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

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    
    
    //////////////////// Overridden Methods ////////////////////
    /**
     * Overridden Method
     * 
     * Computes a hash for this CustomerItem object, using its unique id, to be 
     * used when indexing (i.e., in a hash table).
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
     * Checks whether this CustomerItem object is equal to the given Object. They
     * are considered equal if both objects:
     * - Are instances of the CustomerItem class
     * - Have equal id variables
     * 
     * @param object The Object being compared to this one
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CustomerItem)) {
            return false;
        }
        CustomerItem other = (CustomerItem) object;
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
        return "com.CyberSale.entitypackage.CustomerItem[ id=" + id + " ]";
    }
    
}
