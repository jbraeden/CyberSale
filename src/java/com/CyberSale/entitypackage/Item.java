/*
 * Created by Joseph Sebastian on 2016.04.03  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.entitypackage;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
 * @author Braeden
 */
@Entity
@Table(name = "Item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
    @NamedQuery(name = "Item.findByIdentifierType", query = "SELECT i FROM Item i WHERE i.identifierType = :identifierType"),
    @NamedQuery(name = "Item.findByIdentifierValue", query = "SELECT i FROM Item i WHERE i.identifierValue = :identifierValue"),
    @NamedQuery(name = "Item.findByCost", query = "SELECT i FROM Item i WHERE i.cost = :cost"),
    @NamedQuery(name = "Item.findByPosted", query = "SELECT i FROM Item i WHERE i.posted = :posted"),
    @NamedQuery(name = "Item.findBySold", query = "SELECT i FROM Item i WHERE i.sold = :sold")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 200)
    @Column(name = "identifierType")
    private String identifierType;
    @Column(name = "identifierValue")
    private Integer identifierValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost")
    private int cost;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "posted")
    @Temporal(TemporalType.DATE)
    private Date posted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sold")
    private boolean sold;
    @OneToMany(mappedBy = "itemId")
    private Collection<ItemPhoto> itemPhotoCollection;

    public Item() {
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Item(Integer id, int cost, String description, Date posted, boolean sold) {
        this.id = id;
        this.cost = cost;
        this.description = description;
        this.posted = posted;
        this.sold = sold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public Integer getIdentifierValue() {
        return identifierValue;
    }

    public void setIdentifierValue(Integer identifierValue) {
        this.identifierValue = identifierValue;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public boolean getSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
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
