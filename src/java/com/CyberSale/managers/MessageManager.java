/*
 * Created by Ryan Asper on 2016.04.24  * 
 * Copyright © 2016 Ryan Asper. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.entitypackage.Item;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.CustomerItemFacade;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import com.CyberSale.sessionbeanpackage.ItemPhotoFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ryan Asper
 * @author Patrick Abod
 * @author Shawn Amjad
 * 
 * This bean class is used to handle all messaging capabilities,
 * including when a user wants to contact the seller of an item
 * or wants to share an item with a friend.
 */
@Named(value = "messageManager")
@SessionScoped
public class MessageManager implements Serializable {
    
    /* Message Fields */
    private Customer seller;
    private String subject;
    private String message;    
    private String recipient_address;
    private String share_recipient;
    private Item share_item;
    private boolean attach_image;
    
    /* Java Bean that is initialized at runtime */
    @EJB
    private ItemFacade itemFacade;
    
    /* Java Bean that is initialized at runtime */
    @EJB
    private CustomerItemFacade customerItemFacade;
    
    /* Java Bean that is initialized at runtime */
    @EJB
    private ItemPhotoFacade customerItemPhotoFacade;
    
    /**
     * Create new instance of the MessageManager bean
     * 
     * Initialize necessary variables.
     */
    public MessageManager() {
        subject = message = recipient_address = "";
        
        share_recipient = "";
        attach_image = false;
    }
    
    /**
     * This method is called when the web page is loaded to 
     * populate the email fields with default values.
     * @param itemId The item id of the subject item of the email
     */
    public void OnLoad(int itemId) {
        seller = customerItemFacade.findItemSeller(itemId);
        
        recipient_address = seller.getEmail();
        
        subject = "Item Inquiry: " + itemFacade.findItemById(itemId).getItemName();
    }

    /**
     * This method is used to populate the email fields of the
     * share item page with default values
     * @param itemId The item id of the subject item of the email
     */
    public void OnShareLoad(int itemId) {
        share_item = itemFacade.findItemById(itemId);
        subject = "Look at this item on CyberSale: " + share_item.getItemName();
        
        share_recipient = message = "";
        attach_image = false;
    }
    
    /**
     * This method is used to send the message typed by the user
     * @return The page redirect to the email sent page
     */
    public String sendMessage() {
        boolean success = false;
        
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
                
        try {            
            Session session = Session.getInstance(properties, 
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Constants.CYBERSALE_EMAIL, 
                                    Constants.CYBERSALE_EMAIL_PW);
                        }
                    });
        
            // Create Message object
            MimeMessage emailMessage = new MimeMessage(session);
            
            // Set From & To Fields
            emailMessage.setFrom(new InternetAddress(Constants.CYBERSALE_EMAIL));
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient_address));
            
            // Set Subject
            emailMessage.setSubject(subject);
            
            // Set Message
            message += Constants.CYBERSALE_EMAIL_FOOTER;
            emailMessage.setText(message);
            
            // Send the message
            Transport.send(emailMessage);
            success = true;
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        if (success)
            return "EmailSent?faces-redirect=true";
        else {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email Error", "Error while sending Email."));
            return "";
        }
    }
    
    /**
     * Used to send the share item message typed by the user
     * @return The web page redirection string to the email set web page
     */
    public String sendShareMessage() {
        boolean success = false;
        
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        try {            
            Session session = Session.getInstance(properties, 
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Constants.CYBERSALE_EMAIL, 
                                    Constants.CYBERSALE_EMAIL_PW);
                        }
                    });
        
            // Create Message object
            MimeMessage emailMessage = new MimeMessage(session);
            
            // Set From & To Fields
            emailMessage.setFrom(new InternetAddress(Constants.CYBERSALE_EMAIL));
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(share_recipient));
            
            // Set Subject
            emailMessage.setSubject(subject);
            
            // Create MimeMultiPart and MimeBodyPart objects
            Multipart multipart = new MimeMultipart();
            BodyPart htmlPart = new MimeBodyPart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            
            // Set Message
            message += Constants.CYBERSALE_EMAIL_FOOTER;
            htmlPart.setText(message);
            multipart.addBodyPart(htmlPart);
            
            // Set Attachment (if any)
            if (attach_image && customerItemPhotoFacade.findPhotosForItem(share_item.getId()).size() > 0) {
                String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ItemPhotos/" + share_item.getId() + "/" +
                        customerItemPhotoFacade.findPhotosForItem(share_item.getId()).get(0).getFileName());
                attachmentPart.attachFile(filePath);
                multipart.addBodyPart(attachmentPart);
            }
            
            // Send the message
            emailMessage.setContent(multipart);
            Transport.send(emailMessage);
            success = true;
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        if (success)
            return "EmailSent?faces-redirect=true";
        else {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email Error", "Error while sending Email."));
            return "";
        }
    }

    /*
    Getters & Setters
     */

    public boolean hasImage() {
        return (customerItemPhotoFacade.findPhotosForItem(share_item.getId()).size() > 0);
    }
    
    public boolean isAttach_image() {
        return attach_image;
    }

    public void setAttach_image(boolean attach_image) {
        this.attach_image = attach_image;
    }
    
    public String getShare_recipient() {
        return share_recipient;
    }

    public void setShare_recipient(String share_recipient) {
        this.share_recipient = share_recipient;
    }
    
    public String getSellerName() {    
        return seller.getFirstName() + " " + seller.getLastName();
    }
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
