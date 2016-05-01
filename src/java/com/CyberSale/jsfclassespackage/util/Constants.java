/*
 * Created by CyberSale on 2016.04.13  * 
 * Copyright © 2016 CyberSale. All rights reserved. * 
 */
package com.CyberSale.jsfclassespackage.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Constants.java is a Java Class file that contains public, static constants
 * that are used throughout the CyberSale application.
 * 
 * Keeping all these constants encapsulated in one class allows us to modularize
 * our application and allow for easier maintenance in the future.
 * 
 * @author Ryan Asper
 * @author patrickabod
 */
public class Constants {
    
    /**
     * Stored list of available security questions that user may choose from
     * 
     * Used in Register and RecoverEmail xhtml pages.
     */
    public static final String[] QUESTIONS = {"In what city were you born?",
    "What elementary school did you attend?",
    "What is the last name of your most favorite teacher?",
    "What is your father's middle name?",
    "What is your most favorite pet's name?"}; 
    
    /**
     * Stored list of categories that items may belong to.
     * 
     * Used in the AddItem xhtml page.
     */
    public static final String[] ITEM_CATEGORY = {"Appliances", "Automotives",
        "Books", "Clothing", "Electronics", "Home Goods", "Sports and Outdoors", "Tools",
        "Toys", "Other"};
    
    /**
     * Stored lists of categories that users may search from.
     * 
     * Used in search bar functionality (i.e., default_header xhtml page).
     */
    public static final  String[] SEARCH = {"All", "Appliances", "Automotives",
        "Books", "Clothing", "Electronics", "Home Goods", "Sports and Outdoors", "Tools",
        "Toys", "Other"};
    
    public static final ArrayList<String> SEARCH_CATEGORY = new ArrayList<>(Arrays.asList(SEARCH));
    
    /**
     * Stored list of product types that posted items may belong to.
     * 
     * Used in AddItem xhtml page.
     */
    public static final String[] PRODUCT_CODE_TYPE = {"UPC", "ASIN", "ISBN"};
    
    
    /**
     * Used when saving files (image files) to the file system.
     */
    public static final String ROOT_DIRECTORY = "/Users/patrickabod/ItemPhotos";    
    
    
    /**
     * These constants are used in email communications 
     * (i.e., ContactSeller and ShareItem xhtml pages)
     */
    // The CyberSale email address that all emails will be sent from
    public static final String CYBERSALE_EMAIL = "noreply.cybersale@gmail.com";
    
    // The plaintext password for the CyberSale Email account (bad practice!!)
    public static final String CYBERSALE_EMAIL_PW = "csd@VT(S16)";
    
    // Appended to the end of all emails sent by the CyberSale email address
    public static final String CYBERSALE_EMAIL_FOOTER = "\n\n--\nThis message was sent from a CyberSale user"
            + "\n\nhttp://venus.cs.vt.edu/CyberSale/";
}
