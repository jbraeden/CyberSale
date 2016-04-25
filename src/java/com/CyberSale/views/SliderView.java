/*
 */
package com.CyberSale.views;

import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class SliderView {
    
    private int number1 = 300;   
 
    public int getNumber1() {
        return number1;
    }
 
    public void setNumber1(int number1) {
        this.number1 = number1;
    }
}