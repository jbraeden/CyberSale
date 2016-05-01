/*
 * Created by CyberSale on 2016.04.19  * 
 * Copyright © 2016 CyberSale. All rights reserved. * 
 */
package com.CyberSale.entitypackage;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class that encapsulates the Object Relational Mapping (ORM) of the
 * 'Customer' table in the relational database 'CyberSaleDB'.
 * 
 * The class also specifies Named Queries (predefined SQL queries) to execute
 * on CyberSaleDB, using the '@NamedQuery' annotation.
 * 
 * Logically, Customer represents all data directly related to a user that has
 * created an account on the CyberSale website (such as his or her name, zipcode
 * email address, etc).
 * 
 * @author Ryan Asper
 * @author patrickabod
 */
@Entity
@Table(name = "Customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByUsername", query = "SELECT c FROM Customer c WHERE c.username = :username"),
    @NamedQuery(name = "Customer.findByPassword", query = "SELECT c FROM Customer c WHERE c.password = :password"),
    @NamedQuery(name = "Customer.findByFirstName", query = "SELECT c FROM Customer c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "Customer.findByLastName", query = "SELECT c FROM Customer c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "Customer.findByZipcode", query = "SELECT c FROM Customer c WHERE c.zipcode = :zipcode"),
    @NamedQuery(name = "Customer.findBySecurityQuestion", query = "SELECT c FROM Customer c WHERE c.securityQuestion = :securityQuestion"),
    @NamedQuery(name = "Customer.findBySecurityAnswer", query = "SELECT c FROM Customer c WHERE c.securityAnswer = :securityAnswer"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findByPhoneNumber", query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    /* Private variables that map to columns in the 'Customer' table */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zipcode")
    private int zipcode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "security_question")
    private int securityQuestion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "security_answer")
    private String securityAnswer;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "email")
    private String email;
    @Size(max = 15)
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<CustomerItem> customerItemCollection;

    
    /**
     * Default constructor
     * 
     * Initializes a new instance of the Customer class
     */
    public Customer() {
        // Required empty constructor
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the Customer class with the given Customer
     * id (unique, private integer key that specifies a record in the DB table).
     * 
     * @param id The unique, customer id that specifies an individual Customer record
     */
    public Customer(Integer id) {
        this.id = id;
    }

    /**
     * Overloaded constructor
     * 
     * Initializes a new instance of the Customer class with the given Customer
     * attributes.
     * 
     * @param id The unique, customer id that specifies an individual Customer record
     * @param username The username for the Customer (used to Login)
     * @param password The password for the Customer (used to Login)
     * @param firstName The first name of the Customer
     * @param lastName The last name of the Customer
     * @param zipcode The zipcode in which the Customer resides (used to geolocate items they post)
     * @param securityQuestion The security question chosen by the Customer (used to recover password)
     * @param securityAnswer The security answer given by the Customer (used to recover password)
     * @param email The email of the Customer (used for email communications - i.e. ContactSeller)
     */
    public Customer(Integer id, String username, String password, String firstName, String lastName, int zipcode, int securityQuestion, String securityAnswer, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipcode = zipcode;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.email = email;
    }

    
    
    /////////////// Getters & Setters //////////////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(int securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
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

    @XmlTransient
    public Collection<CustomerItem> getCustomerItemCollection() {
        return customerItemCollection;
    }

    public void setCustomerItemCollection(Collection<CustomerItem> customerItemCollection) {
        this.customerItemCollection = customerItemCollection;
    }

    
    
    //////////////////// Overridden Methods ////////////////////
    /**
     * Overridden Method
     * 
     * Computes a hash for this Customer object, using its unique id, to be used
     * when indexing (i.e., in a hash table).
     * 
     * @return The computed hash value.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Overridden Method
     * 
     * Checks whether this Customer object is equal to the given Object. They
     * are considered equal if both objects:
     * - Are instances of the Customer class
     * - Have equal id variables
     * 
     * @param object The Object being compared to this one
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Overridden Method
     * 
     * Returns a string representation of this object that is essentially the
     * package name and the id of the object.
     * 
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "com.CyberSale.entitypackage.Customer[ id=" + id + " ]";
    }
    
}
