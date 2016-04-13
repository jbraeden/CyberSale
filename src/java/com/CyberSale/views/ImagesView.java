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
 *
 * @author cloud
 */
@ManagedBean
public class ImagesView {
     
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 2; i <= 2; i++) {
            images.add("banner" + i + ".png");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}
