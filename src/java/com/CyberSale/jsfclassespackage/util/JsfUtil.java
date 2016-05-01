/*
 * Created by CyberSale on 2016.04.13  * 
 * Copyright © 2016 CyberSale. All rights reserved. * 
 */
package com.CyberSale.jsfclassespackage.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

/**
 * JsfUtil.java is a Java class that contains many public, static methods that
 * add functionality to the Java Server Faces (JSF) library.
 * 
 * JsfUtil contains methods that enable the developer to add JSF Messages and
 * Requests to the current FacesContext.
 * 
 * @author Ryan Asper
 */
public class JsfUtil {

    /**
     * Builds and Returns a SelectItem array given the List of entities.
     * 
     * @param entities The list of entities to select from
     * @param selectOne Whether or not the list should have a default selection
     * @return The SelectItem array representing the List of entities.
     */
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    
    ////////////// JSF Messages Methods //////////////////
    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    
    //////////////// JSF Requests Methods /////////////////////
    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

}
