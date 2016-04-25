/*
 * Created by Patrick Abod on 2016.04.13  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.jsfclassespackage.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author patrickabod
 */
public class Constants {
    public static final String[] QUESTIONS = {"In what city were you born?",
    "What elementary school did you attend?",
    "What is the last name of your most favorite teacher?",
    "What is your father's middle name?",
    "What is your most favorite pet's name?"}; 
    
    public static final String[] ITEM_CATEGORY = {"Appliances", "Automotives",
        "Books", "Clothing", "Electronics", "Home Goods", "Sports and Outdoors", "Tools",
        "Toys", "Other"};
    
    public static final  String[] SEARCH = {"All", "Appliances", "Automotives",
        "Books", "Clothing", "Electronics", "Home Goods", "Sports and Outdoors", "Tools",
        "Toys", "Other"};
    
    public static final ArrayList<String> SEARCH_CATEGORY = new ArrayList<>(Arrays.asList(SEARCH));
    
    public static final String[] PRODUCT_CODE_TYPE = {"UPC", "ASIN", "ISBN"};
    
    public static final String ROOT_DIRECTORY = "C:/Users/Asper/ItemPhotos";    
    
    public static final String CYBERSALE_EMAIL = "noreply.cybersale@gmail.com";
    
    public static final String CYBERSALE_EMAIL_PW = "csd@VT(S16)";
}
