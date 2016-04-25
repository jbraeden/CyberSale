/*
 * Created by Ryan Asper on 2016.04.24  * 
 * Copyright © 2016 Ryan Asper. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.CustomerItemFacade;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ryan Asper
 */
@Named(value = "messageManager")
@SessionScoped
public class MessageManager implements Serializable {
    
    /* Message Fields */
    private Customer seller;
    private String subject;
    private String message;    
    private String recipient_address;
    
    @EJB
    private ItemFacade itemFacade;
    
    @EJB
    private CustomerItemFacade customerItemFacade;
    
    /**
     * Create new instance of the MessageManager bean
     * 
     * Initialize necessary variables.
     */
    public MessageManager() {
        subject = message = recipient_address = "";
    }

    public void OnLoad(int itemId) {
        seller = customerItemFacade.findItemSeller(itemId);
        
        recipient_address = seller.getEmail();
        
        subject = "Item Inquiry: " + itemFacade.findItemById(itemId).getItemName();
    }
    
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
            emailMessage.setText(message + Constants.CYBERSALE_EMAIL_FOOTER);
            
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

    /*
    Getters & Setters
     */
    
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
