/*
 * Created by Joe Fletcher on 2016.04.09  * 
 * Copyright © 2016 Joe Fletcher. All rights reserved. * 
 */
package com.CyberSale.views;

import javax.faces.bean.ManagedBean;
 
/**
 * This managed bean class is used for the zip code based filtering in Search 
 * feature
 * @author cloud
 */
@ManagedBean
public class SliderView {
    
    // The default number and max number of miles away
    private int number1 = 300;   
 
    /**
     * Getter method for number1 field value
     * @return the number1 value
     */
    public int getNumber1() {
        return number1;
    }
 
    /**
     * Sets the number1 field 
     * @param number1 the value to be set
     */
    public void setNumber1(int number1) {
        this.number1 = number1;
    }
}