/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import com.CyberSale.entitypackage.Item;
import com.CyberSale.entitypackage.ItemPhoto;
import com.CyberSale.entitypackage.Photo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Braeden
 * @author Patrick Abod
 * @author Shawn Amjad
 * 
 * This class is used for all DB queries to the Item-Photo
 * relational table
 */
@Stateless
public class ItemPhotoFacade extends AbstractFacade<ItemPhoto> {

    @PersistenceContext(unitName = "CyberSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemPhotoFacade() {
        super(ItemPhoto.class);
    }
    
    /*
        The following queries are added to the generated code
    */
    
    /**
     * Find all photos for a particular item
     * Can be null since photos for an item are optional
     * @param itemId the item id on which to find photos
     * @return the list of photos, null if none are found
     */
    public List<Photo> findPhotosForItem(Integer itemId) {
        List<ItemPhoto> itemPhotos = null;
        try {
            itemPhotos = em.createNamedQuery("ItemPhoto.findItemPhotoByItem", ItemPhoto.class)
                    .setParameter("itemId", itemId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<Photo> photos = new ArrayList<>();
        if (itemPhotos != null && !itemPhotos.isEmpty()) {
            for (ItemPhoto ip : itemPhotos) {
                photos.add(ip.getPhotoId());
            }
            return photos;
        }
        return null;
    }
    
    /**
     * Find the item to which a photo belongs
     * @param photoId the photo id on which to search
     * @return the item to which a photo belongs
     * Should never be null
     */
    public Item findItemForPhoto(Integer photoId) {
        ItemPhoto itemPhoto = null;
        try {
            itemPhoto = em.createNamedQuery("findItemPhotoByPhoto", ItemPhoto.class)
                    .setParameter("photoId", photoId)
                    .getResultList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Item item = null;
        if (itemPhoto != null) {
            try {
                item = em.find(Item.class, itemPhoto.getItemId().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return item;
    } 
}
