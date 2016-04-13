/*
 * Created by Patrick Abod on 2016.04.13  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.CustomerFacade;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author patrickabod
 */
@Named(value = "customerManager")
@SessionScoped
public class CustomerManager implements Serializable {
    
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int zipcode;
    private int securityQuestionKey;
    private Map<String, Object> securityQuestions;
    private String securityQuestionAnswer;
    private String email;

    @EJB
    private CustomerFacade customerFacade;
    
    
    /**
     * Creates a new instance of CustomerManager
     */
    public CustomerManager() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public Map<String, Object> getSecurityQuestions() {
        if (securityQuestions == null) {
            securityQuestions = new LinkedHashMap<>();
            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                securityQuestions.put(Constants.QUESTIONS[i], i);
            }
        }
        return securityQuestions;
    }
    
    public int getSecurityQuestionKey() {
        return securityQuestionKey;
    }

    public void setSecurityQuestionKey(int securityQuestionKey) {
        this.securityQuestionKey = securityQuestionKey;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    private CustomerFacade getFacade() {
        return customerFacade;
    }
    
    /*
        CRUD operations
    */
    
    public Customer create(Customer i) {
        try {
            getFacade().create(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Customer read(Customer i) {
        try {
            getFacade().find(i.getId());
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Customer update(Customer i) {
        try {
            getFacade().edit(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Customer delete(Customer i) {
        try {
            getFacade().remove(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /*
        Additional Customer Table Queries
    */
    
}
