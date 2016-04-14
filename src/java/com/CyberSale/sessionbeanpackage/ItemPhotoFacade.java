/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
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
    
    public List<Photo> findPhotosForItem(Integer itemId) {
        List<ItemPhoto> itemPhotos = null;
        try {
            itemPhotos = em.createNamedQuery("findItemPhotoByItem", ItemPhoto.class)
                    .setParameter("itemId", itemId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<Photo> comments = new ArrayList<>();
        if (itemPhotos != null && !itemPhotos.isEmpty()) {
            for (ItemPhoto ip : itemPhotos) {
                if ((ip.getItemId().getId()).equals(itemId)) {
                    try {
                        comments.add(em.find(Photo.class, ip.getPhotoId().getId()));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return comments;
        }
        return null;
    }
    
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
