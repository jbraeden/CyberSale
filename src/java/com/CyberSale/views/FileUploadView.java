/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CyberSale.views;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
 
/**
 * This managed bean class is used to Handle File Uploads. In this case 
 * specific to images for the Items.
 * @author cloud
 */
@ManagedBean
public class FileUploadView {
 
    /**
     * Confirms to that the File Uploading was handled and no issues occured.
     * @param event the FileUploadEvent object that we need to handle for uploading
     * its file
     */
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}