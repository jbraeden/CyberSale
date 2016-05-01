/*
 * Created by CyberSale on 2016.04.19  * 
 * Copyright © 2016 CyberSale. All rights reserved. * 
 */
package com.CyberSale.entitypackage;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class that encapsulates the Object Relational Mapping (ORM) of the
 * 'Photo' table in the relational database 'CyberSaleDB'.
 * 
 * The class also specifies Named Queries (predefined SQL queries) to execute
 * on CyberSaleDB, using the '@NamedQuery' annotation.
 * 
 * Logically, Photo represents all data directly associated with a Photo that 
 * has been uploaded alongside an item (such as the Photo's filename that 
 * identifies it on the server's file-system).
 * 
 * @author Ryan Asper
 * @author patrickabod
 */
@Entity
@Table(name = "Photo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p"),
    @NamedQuery(name = "Photo.findById", query = "SELECT p FROM Photo p WHERE p.id = :id"),
    @NamedQuery(name = "Photo.findByFileName", query = "SELECT p FROM Photo p WHERE p.fileName = :fileName")})
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    /* Private variables that map to columns in the 'Photo' table */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "file_name")
    private String fileName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "photoId")
    private Collection<ItemPhoto> itemPhotoCollection;

    
    /**
     * Default constructor
     * 
     * Initializes a new instance of the Photo class
     */
    public Photo() {
        // Required empty constructor
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the Photo class with the given 
     * Photo id (unique, private integer key that specifies a record in the DB 
     * table).
     * 
     * @param id The unique id that specifies an individual Photo record
     */
    public Photo(Integer id) {
        this.id = id;
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the Photo class with the given Photo
     * attributes.
     * 
     * @param id The unique id that specifies an individual Photo record
     * @param fileName The name of the image file on the server's file-system
     */
    public Photo(Integer id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
    
    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the Photo class with the Photo's filename
     * 
     * @param fileName The name of the image file on the server's file-system
     */
    public Photo(String fileName) {
        this.fileName = fileName;
    }

    
    
    /////////////// Getters & Setters //////////////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * Computes a hash for this Photo object, using its unique id, to be used
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
     * Checks whether this Photo object is equal to the given Object. They
     * are considered equal if both objects:
     * - Are instances of the Photo class
     * - Have equal id variables
     * 
     * @param object The Object being compared to this one
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
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
        return "com.CyberSale.entitypackage.Photo[ id=" + id + " ]";
    }
    
}