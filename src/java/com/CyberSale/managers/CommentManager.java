/*
 * Created by Patrick Abod on 2016.04.13  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Comment;
import com.CyberSale.sessionbeanpackage.CommentFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author patrickabod
 */
@Named(value = "commentManager")
@SessionScoped

public class CommentManager implements Serializable {
    
    private String description;
    
    @EJB
    private CommentFacade commentFacade;

    /**
     * Creates a new instance of CommentManager
     */
    public CommentManager() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public CommentFacade getFacade() {
        return commentFacade;
    }
    
    /*
        CRUD operations for Item
    */
    
    public Comment create(Comment c) {
        try {
            getFacade().create(c);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Comment read(Comment c) {
        try {
            getFacade().find(c.getId());
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Comment update(Comment c) {
        try {
            getFacade().edit(c);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Comment delete(Comment c) {
        try {
            getFacade().remove(c);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    
}
