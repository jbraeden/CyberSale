/*
 * Created by Ryan Asper on 2016.04.24  * 
 * Copyright © 2016 Ryan Asper. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.sessionbeanpackage.CustomerItemFacade;
import com.CyberSale.sessionbeanpackage.ItemFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

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
        subject = message = "";
    }

    public void OnLoad(int itemId) {
        seller = customerItemFacade.findItemSeller(itemId);
        
        subject = "Inquiry: " + itemFacade.findItemById(itemId).getItemName();
    }
    
    public String sendMessage() {
        // TODO
        
        return "";
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
