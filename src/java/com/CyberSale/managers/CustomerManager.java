/*
 * Created by Patrick Abod on 2016.04.13  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.managers;

import com.CyberSale.entitypackage.Customer;
import com.CyberSale.entitypackage.Item;
import com.CyberSale.jsfclassespackage.util.Constants;
import com.CyberSale.sessionbeanpackage.CustomerFacade;
import com.CyberSale.sessionbeanpackage.CustomerItemFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    private String phoneNumber;
    private String statusMessage = "";

    /* Java Bean that is initialized at runtime */
    @EJB
    private CustomerFacade customerFacade;
    
    @EJB
    private CustomerItemFacade customerItemFacade;
    
    /* True when the Customer has logged in */
    private boolean loggedIn;
    
    /* These variables are used when the customer wants to reset his or her password */
    private String email_answer;
    private String question_answer;
    private String newPassword;
    private String confirmPassword;
    
    /* Set to the page the user is attempting to login to (none otherwise) */
    private String loginToPage;
    
    private List<Item> items;
    
    /**
     * Creates a new instance of CustomerManager.
     * 
     * Initialize necessary variables.
     */
    public CustomerManager() {
        loggedIn = false;
        
        // Initialize Customer fields
        username = password = firstName = lastName = "";
        securityQuestionAnswer = email = loginToPage = "";
        zipcode = securityQuestionKey = 0;
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
                
                if (!loginToPage.isEmpty()) {
                    String nextPage = loginToPage;
                    loginToPage = "";
                    return nextPage + "?faces-redirect=true";
                }
                else
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
        securityQuestionAnswer = email = phoneNumber = loginToPage = "";
        zipcode = securityQuestionKey = 0;
        securityQuestions = new HashMap<>();

        // Clear SessionMap of Bean objects
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "index?faces-redirect=true";
    }
    
    public String viewMyItems() {
        Customer c = customerFacade.findCustomerByUsername(username);
        items = new ArrayList<Item>();
        items = customerItemFacade.findUserItems(c.getId());
        Collections.reverse(items);
        return "UserItems?faces-redirect=true";
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

    public void setSecurityQuestions(Map<String, Object> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public Map<String, Object> getSecurityQuestions() {
        if (securityQuestions == null) {
            securityQuestions = new LinkedHashMap<>();
            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                securityQuestions.put(Constants.QUESTIONS[i], i);
            }
            //System.out.println(securityQuestions.toString());
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    private CustomerFacade getFacade() {
        return customerFacade;
    }

    public String getEmail_answer() {
        return email_answer;
    }

    public void setEmail_answer(String email_answer) {
        this.email_answer = email_answer;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getQuestion_answer() {
        return question_answer;
    }

    public void setQuestion_answer(String question_answer) {
        this.question_answer = question_answer;
    }

    public List<Item> getItems() {
        viewMyItems();
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    /*
        CRUD operations
    */
    
    public String createAccount() {
        // Check to see if a user already exists with the username given.
        Customer c = customerFacade.findCustomerByUsername(username);
        
        if (c != null) {
            username = "";
            statusMessage = "Username already exists! Please select a different one!";
            return "";
        }

        if (statusMessage.isEmpty()) {
            try {
                Customer customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setZipcode(zipcode);
                customer.setEmail(email);
                customer.setPhoneNumber(phoneNumber);
                customer.setSecurityQuestion(securityQuestionKey);
                customer.setSecurityAnswer(securityQuestionAnswer);
                customer.setUsername(username);
                customer.setPassword(password);
                customerFacade.create(customer);
            
                // Login the customer
                loggedIn = true;
                initializeSessionMap(customer);
            } catch (EJBException e) {
                username = "";
                statusMessage = "Something went wrong while creating your account!";
                return "";
            }
            
            return "index.xhtml?faces-redirect=true";
        }
        return "";
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
    
    public String emailSubmit() {
        Customer customer = customerFacade.findCustomerByEmail(email_answer);
        if (customer == null) {
            statusMessage = "Entered email does not exist!";
            return "RecoverEmail?faces-redirect=true";
        }
        else {
            statusMessage = "";
            username = customer.getUsername();
            return "SecurityQuestion?faces-redirect=true";
        }
    }
    
    public String securityQuestionSubmit() {
        Customer customer = customerFacade.findCustomerByUsername(username);
        if (customer.getSecurityAnswer().equals(question_answer)) {
            statusMessage = "";
            return "PasswordReset?faces-redirect=true";
        }
        else {
            statusMessage = "Answer incorrect";
            return "SecurityQuestion?faces-redirect=true";
        }
    }
    
    public String getSecurityQuestion() {
        int question = customerFacade.findCustomerByUsername(username).getSecurityQuestion();
        return Constants.QUESTIONS[question];
    }
    
    public String resetPassword() {
        validatePasswords(newPassword, confirmPassword);
        if (statusMessage.equals("")) {
            statusMessage = "";
            Customer customer = customerFacade.findCustomerByEmail(email_answer);
            try {
                customer.setPassword(newPassword);
                customerFacade.edit(customer);
                email_answer = question_answer = newPassword = confirmPassword = "";
                
                // Login the customer
                loggedIn = true;
                initializeSessionMap(customer);
            } catch (EJBException e) {
                statusMessage = "Something went wrong editing your profile, please try again!";
                return "PasswordReset?faces-redirect=true";            
            }
            
            return "index?faces-redirect=true";
        }
        else {
            return "PasswordReset?faces-redirect=true";            
        }
    }
    
    public void validatePasswords(String p1, String p2) {
        if(p1.isEmpty() || p2.isEmpty() || !p1.equals(p2)) {
            statusMessage = "Passwords must match!";
        } else {
            statusMessage = "";
        } 
    }
    
    public String attemptContactSeller() {              
        if (loggedIn)
            return "ContactSeller?faces-redirect=true";
        else {
            loginToPage = "ContactSeller";
            return "Login?faces-redirect=true";
        }
    }
    
    public String attemptPostItem() {              
        if (loggedIn)
            
            return "AddItem?faces-redirect=true";
        else {
            loginToPage = "AddItem";
            return "Login?faces-redirect=true";
        }    
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
              getSessionMap().put("customer_id", customer.getId());
    }
    
}
