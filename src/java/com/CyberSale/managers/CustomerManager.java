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
 * 
 * This manager bean class handles all functions that involve customer actions,
 * including account creation, logging in/out from the application,
 * reseting forgotten passwords, and viewing customer items.
 * 
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
        
    /* Java Bean that is initialized at runtime */
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
    
    /** List of the items to populate the my Items page 
     * (All items the customer has posted)
    */
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
        Log in/out Methods
    */
    
    /**
     * This method is used to login the customer when the login button
     * is clicked in the header.
     * @return The home page or next page URL redirect string
     */
    public String loginCustomer() {
        Customer customer = customerFacade.findCustomerByUsername(username);
        // ensure the customer has an account
        if (customer == null) {
            addErrorMessage("Login Error", "Invalid username or password!");
            return "";
        } 
        else {
            // check that the customer has entered the correct username and password
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                // set the login flag
                loggedIn = true;
                // map the customer's sessison
                initializeSessionMap(customer);
                
                // check to see if the customer wants to login into a specific page
                // such as the post item page
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
    
    /**
     * This method is used to log the customer out of their account
     * when the logout button is clicked in the header
     * @return The home page URL redirect string
     */
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
    
    /* Viewing a customer's history of posted items */
    
    /**
     * This method is used to get the list of items that a customer
     * has posted using his or her account.
     * The list is reversed to display in order with the most 
     * recently posted items at the top.
     * @return The User Items URL redirect page
     */
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
        // map the security questions if they have not yet been mapped
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
    
    /**
     * This method is used to create a customer account
     * when the user clicks the submit button from the register page
     * @return The home page redirect string
     */
    public String createAccount() {
        // Check to see if a user already exists with the username given.
        Customer c = customerFacade.findCustomerByUsername(username);
        
        // the user already has a Cyber Sale account
        if (c != null) {
            username = "";
            statusMessage = "Username already exists! Please select a different one!";
            return "";
        }

        if (statusMessage.isEmpty()) {
            try {
                // create a new customer and set the entered fields
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
                //persist the customer object into the Customer table
                customerFacade.create(customer);
            
                // Login the customer
                loggedIn = true;
                // map the newly created customer
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
    
    /**
     * Read a customer from the DB
     * @param i the customer to read
     * @return the found customer
     */
    public Customer read(Customer i) {
        try {
            getFacade().find(i.getId());
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Update a customer's information in the DB
     * @param i the customer to edit
     * @return the newly updated customer object
     */
    public Customer update(Customer i) {
        try {
            getFacade().edit(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Delete a customer's account from the DB
     * @param i the customer account to delete
     * @return the deleted customer
     */
    public Customer delete(Customer i) {
        try {
            getFacade().remove(i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds an error message for any validation errors during customer operations
     * @param summary Summary of the error
     * @param error The actual error message
     */
    private void addErrorMessage(String summary, String error) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, error));
    }
    
    /**
     * This method is used to verify that the customer has
     * an account with Cyber Sale based on an email query.
     * This method is used during the password reset process.
     * @return the redirect web page string
     */
    public String emailSubmit() {
        Customer customer = customerFacade.findCustomerByEmail(email_answer);
        if (customer == null) {
            // customer entered invalid email
            statusMessage = "Entered email does not exist!";
            return "RecoverEmail?faces-redirect=true";
        }
        else {
            // proceed because email is valid
            statusMessage = "";
            username = customer.getUsername();
            return "SecurityQuestion?faces-redirect=true";
        }
    }
    
    /**
     * This method is used to verify that the customer has entered the
     * correct answer for their security question.
     * @return the redirect web page string
     */
    public String securityQuestionSubmit() {
        Customer customer = customerFacade.findCustomerByUsername(username);
        if (customer.getSecurityAnswer().equals(question_answer)) {
            // proceed
            statusMessage = "";
            return "PasswordReset?faces-redirect=true";
        }
        else {
            // answer is incorrect
            statusMessage = "Answer incorrect";
            return "SecurityQuestion?faces-redirect=true";
        }
    }
    
    /**
     * get the security question from our map of security questions
     * @return 
     */
    public String getSecurityQuestion() {
        int question = customerFacade.findCustomerByUsername(username).getSecurityQuestion();
        return Constants.QUESTIONS[question];
    }
    
    /**
     * This method is used to finally reset the customer's password
     * @return The home page redirect string
     */
    public String resetPassword() {
        // make sure the passwords match
        validatePasswords(newPassword, confirmPassword);
        if (statusMessage.equals("")) {
            statusMessage = "";
            Customer customer = customerFacade.findCustomerByEmail(email_answer);
            try {
                // reset the password
                customer.setPassword(newPassword);
                customerFacade.edit(customer);
                // reset the information for customer password reset
                email_answer = question_answer = newPassword = confirmPassword = "";
                
                // Login the customer
                loggedIn = true;
                // map the customer
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
    
    /**
     * This method is used to ensure the passwords are the same and not empty.
     * Sets the status message if there are any issues.
     * @param p1 the first password input
     * @param p2 the confirmation password
     */
    public void validatePasswords(String p1, String p2) {
        if(p1.isEmpty() || p2.isEmpty() || !p1.equals(p2)) {
            statusMessage = "Passwords must match!";
        } else {
            statusMessage = "";
        } 
    }
    
    /**
     * This method is used to bring the customer to the contact seller web page
     * if the customer is logged in
     * @return the web page redirect string
     */
    public String attemptContactSeller() {              
        if (loggedIn)
            return "ContactSeller?faces-redirect=true";
        else {
            loginToPage = "ContactSeller";
            return "Login?faces-redirect=true";
        }
    }
    
    /**
     * This method is used to bring the customer to the post item web page
     * if the customer is logged in
     * @return the web page redirect string
     */
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
