/*
 * Created by Joseph Sebastian on 2016.02.14  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */
package com.CyberSale.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
/**
 *
 * @author Braeden
 */
public class EmailValidator implements Validator {

    /**
     * Makes sure the email address is a valid one.
     * @param context the FacesContext object
     * @param component the UIComponent object
     * @param value the Object value representing the user's email
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        // Typecast the password "value" entered by the user to String.
        String email = (String) value;

        if (email == null || email.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }
       /**
        * regex stands for REGular EXpression, which defines a search pattern for strings.
        * To learn about how to use regex, see https://docs.oracle.com/javase/tutorial/essential/regex/
        **/
        
        // Validate if the email address entered by the user is in the right format.
        String regex = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)";
        if (!email.matches(regex)) {
            throw new ValidatorException(new FacesMessage("Please Enter a Valid Email Address!"));
        }        
    } 
    
}