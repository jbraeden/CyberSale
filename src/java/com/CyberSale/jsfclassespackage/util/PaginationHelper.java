/*
 * Created by CyberSale on 2016.04.13  * 
 * Copyright © 2016 CyberSale. All rights reserved. * 
 */
package com.CyberSale.jsfclassespackage.util;

import javax.faces.model.DataModel;

/**
 * PaginationHelper.java is a Java class that contains helper methods to assist
 * in the Primefaces Carousel UI element functionality.
 * 
 * This class contains methods that allow the Carousel to determine what page
 * it's currently on, how many pages there are total, etc.
 * 
 * @author Ryan Asper
 */
public abstract class PaginationHelper {

    private int pageSize;
    private int page;

    /**
     * Default Constructor
     * 
     * Initializes a new instance of the PaginationHelper class given the
     * page size.
     * 
     * @param pageSize The size of one page
     */
    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    
    ////////////////// Getters & Setters ///////////////////////
    public abstract int getItemsCount();

    public abstract DataModel createPageDataModel();

    public int getPageFirstItem() {
        return page * pageSize;
    }

    public int getPageLastItem() {
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    public boolean isHasPreviousPage() {
        return page > 0;
    }

    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

}
