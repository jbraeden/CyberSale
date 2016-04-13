/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
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
 *
 * @author Braeden
 */
@Entity
@Table(name = "ItemPhoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemPhoto.findAll", query = "SELECT i FROM ItemPhoto i"),
    @NamedQuery(name = "ItemPhoto.findById", query = "SELECT i FROM ItemPhoto i WHERE i.id = :id"),
    @NamedQuery(name = "ItemPhoto.findItemPhotoByItem", query = "SELECT i FROM ItemPhoto i WHERE i.itemId = :itemId"),
    @NamedQuery(name = "ItemPhoto.findItemPhotoByPhoto", query = "SELECT i FROM ItemPhoto i WHERE i.photoId = :photoId"),

})
public class ItemPhoto implements Serializable {

    private static final long serialVersionUID = 1L;
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

    public ItemPhoto() {
    }

    public ItemPhoto(Integer id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemPhoto)) {
            return false;
        }
        ItemPhoto other = (ItemPhoto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.CyberSale.entitypackage.ItemPhoto[ id=" + id + " ]";
    }
    
}
