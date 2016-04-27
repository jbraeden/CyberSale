/*
 * Created by Osman Balci on 2016.02.14  * 
 * Copyright © 2016 Osman Balci. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.entitypackage.Item;
import com.CyberSale.entitypackage.ItemPhoto;
import com.CyberSale.entitypackage.Photo;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.CustomerFacade;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import com.CyberSale.sessionbeanpackage.ItemPhotoFacade;
import com.CyberSale.sessionbeanpackage.PhotoFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "fileManager")
@ManagedBean
@SessionScoped
/**
 *
 * @author Balci
 */
public class PhotoManager {

    // Instance Variables (Properties)
    private UploadedFile file;
    private String message = "";
    
    /**
     * The instance variable 'customerFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserFacade.
     */
    @EJB
    private ItemFacade itemFacade;
    
    @EJB
    private ItemPhotoFacade itemPhotoFacade;

    /**
     * The instance variable 'photoFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean PhotoFacade.
     */
    @EJB
    private PhotoFacade photoFacade;

    // Returns the uploaded file
    public UploadedFile getFile() {
        return file;
    }

    // Obtains the uploaded file
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    // Returns the message
    public String getMessage() {
        return message;
    }

    // Obtains the message
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * "Profile?faces-redirect=true" asks the web browser to display the
     * Profile.xhtml page and update the URL corresponding to that page.
     * @return Profile.xhtml or nothing
     */

    public String upload() {
        if (file.getSize() != 0) {
            copyFile(file);
            message = "";
            return "ItemDetail?faces-redirect=true";
        } else {
            message = "You need to upload a file first!";
            return "";
        }
    }
    
    public String cancel() {
        message = "";
        return "ItemDetail?faces-redirect=true";
    }

    public FacesMessage copyFile(UploadedFile file) {
        try {            
            InputStream in = file.getInputstream();
            
            File tempFile = inputStreamToFile(in, file.getFileName());
            in.close();

            FacesMessage resultMsg;

            Integer itemId = (Integer) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("item_id");

            Item item = itemFacade.findItemById(itemId);

            // Insert photo record into database
            String extension = ".png";

            
            Photo photo = new Photo(file.getFileName());
            
            photoFacade.create(photo);
            
            ItemPhoto itemPhoto = new ItemPhoto();
            itemPhoto.setItemId(item);
            itemPhoto.setPhotoId(photo);
            
            itemPhotoFacade.create(itemPhoto);
            
            this.file = null;
            
            resultMsg = new FacesMessage("Success!", "File Successfully Uploaded!");
            return resultMsg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FacesMessage("Upload failure!",
            "There was a problem reading the image file. Please try again with a new photo file.");
    }

    private File inputStreamToFile(InputStream inputStream, String childName)
            throws IOException {
        // Read in the series of bytes from the input stream
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);

        String filePath = Constants.ROOT_DIRECTORY + "/" + (Integer) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("item_id");
        
        new File(filePath).mkdir();
        
        // Write the series of bytes on file.
        File targetFile = new File(filePath, childName);

        OutputStream outStream;
        outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        outStream.close();

        // Save reference to the current image.
        return targetFile;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.file = event.getFile();
        upload();
    }
    
    /*
         Get Photo Filename given a Photo ID
    */    
    public String getItemPhotoFilename(int itemID) {
        
        List<Photo> photos = itemPhotoFacade.findPhotosForItem(itemID);

        if (photos != null)
            return "/ItemPhotos/" + itemID + "/" + photos.get(0).getFileName();
        else
            return "/resources/images/default_item_photo.png";
    }
}