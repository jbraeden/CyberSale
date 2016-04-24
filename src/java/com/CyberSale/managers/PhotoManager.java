/*
 * Created by Patrick Abod on 2016.04.13  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Photo;
import com.CyberSale.sessionbeanpackage.ItemPhotoFacade;
import com.CyberSale.sessionbeanpackage.PhotoFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author patrickabod
 */
@Named(value = "photoManager")
@SessionScoped
public class PhotoManager implements Serializable {
    
    private String fileName;
    
    @EJB
    private PhotoFacade photoFacade;
    
    @EJB
    private ItemPhotoFacade itemPhotoFacade;

    /**
     * Creates a new instance of PhotoManager
     */
    public PhotoManager() {
    }

    
    /*
        Get Photo Filename given a Photo ID
    */    
    public String getItemPhotoFilename(int itemID) {
        List<Photo> photos = itemPhotoFacade.findPhotosForItem(itemID);
        
        if (photos != null)
            return photos.get(0).getFileName();
        else
            return "default_photo.png";
    }
        
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public boolean upload(String filePath) {
        // TODO: implement
        return false;
    }
    
    private PhotoFacade getFacade() {
        return photoFacade;
    }
    
    /*
        CRUD operations for Item
    */
    
    public Photo create(Photo p) {
        try {
            getFacade().create(p);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Photo read(Photo p) {
        try {
            getFacade().find(p.getId());
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Photo update(Photo p) {
        try {
            getFacade().edit(p);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Photo delete(Photo p) {
        try {
            getFacade().remove(p);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
        
}
