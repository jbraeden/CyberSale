/*
 * Created by Patrick Abod on 2016.04.13  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.CustomerFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 *
 * @author patrickabod
 */
@Named(value = "customerManager")
@SessionScoped
public class CustomerManager implements Serializable {
    
    /* Customer Fields */
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int zipcode;
    private int securityQuestionKey;
    private Map<String, Object> securityQuestions;
    private String securityQuestionAnswer;
    private String email;

    /* Java Bean that is initialized at runtime */
    @EJB
    private CustomerFacade customerFacade;
    
    /* True when the Customer has logged in */
    private boolean loggedIn;
    
    /**
     * Creates a new instance of CustomerManager.
     * 
     * Initialize necessary variables.
     */
    public CustomerManager() {
        loggedIn = false;
        
        // Initialize Customer fields
        username = password = firstName = lastName = "";
        securityQuestionAnswer = email = "";
        zipcode = securityQuestionKey = 0;
        securityQuestions = new HashMap<>();
    }

    /*
        Public Methods
    */
    
    public String loginCustomer() {
        Customer customer = customerFacade.findCustomerByUsername(username);
        
        if (customer == null) {
            addErrorMessage("Login Error", "Invalid username or password!");
            return "";
        } 
        else {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                loggedIn = true;
                initializeSessionMap(customer);
                return "index?faces-redirect=true";
            }

            addErrorMessage("Login Error", "Invalid username or password!");
            return "";
        }
    }
    
    public String logoutCustomer() {
        // Reset Fields
        loggedIn = false;
        username = password = firstName = lastName = "";
        securityQuestionAnswer = email = "";
        zipcode = securityQuestionKey = 0;
        securityQuestions = new HashMap<>();

        // Clear SessionMap of Bean objects
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "index?faces-redirect=true";
    }

    

    /*
    Setters & Getters
     */

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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
    
    
    /*
        Private Methods
    */
    
    /**
     * 
     * @param error 
     */
    private void addErrorMessage(String summary, String error) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, error));
    }
    
    /**
     * Initializes the Session variable for the customer bean.
     * 
     * @param customer The Customer object that has been initialized.
     */
    private void initializeSessionMap(Customer customer) {
      FacesContext.getCurrentInstance().getExternalContext().
              getSessionMap().put("first_name", customer.getFirstName());
      FacesContext.getCurrentInstance().getExternalContext().
              getSessionMap().put("last_name", customer.getLastName());
      FacesContext.getCurrentInstance().getExternalContext().
              getSessionMap().put("username", username);
      FacesContext.getCurrentInstance().getExternalContext().
              getSessionMap().put("user_id", customer.getId());
    }
}
