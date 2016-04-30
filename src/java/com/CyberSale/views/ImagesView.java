/*
 * Created by Joe Fletcher on 2016.04.09  * 
 * Copyright © 2016 Joe Fletcher. All rights reserved. * 
 */
package com.CyberSale.views;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 * This ManagedBean class is used for displaying the Banner Image. Previously we
 * were using different Images which would rotate in the caroussel however we 
 * are only using one image for our final web app.
 * @author cloud
 */
@ManagedBean
public class ImagesView {
     
    // Gloabl List reference to the image/banner names
    private List<String> images;
     
    /**
     * Method called as soon as the class is initialized in which the images List
     * is populated
     */
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 2; i <= 2; i++) {
            images.add("banner" + i + ".png");
        }
    }
 
    /**
     * Returns the list of Image/Banner file names
     * @return List of images/banner names
     */
    public List<String> getImages() {
        return images;
    }
}
