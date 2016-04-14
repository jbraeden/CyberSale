/* 
 * Created by Patrick Abod on 2016.04.07  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
/**
 * Author:  patrickabod
 * Created: Apr 7, 2016
 */

DROP TABLE IF EXISTS ItemPhoto;
DROP TABLE IF EXISTS ItemComment;
DROP TABLE IF EXISTS ItemCustomer;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Photo;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Customer;

CREATE TABLE Comment
(
    id INT NOT NULL AUTO_INCREMENT,
    description TEXT (512) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Photo
(
    id INT NOT NULL AUTO_INCREMENT,
    file_path VARCHAR (256) NOT NULL,
    PRIMARY KEY(id)
);

/* Items up for sale*/
CREATE TABLE Item 
(
	id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(256) NOT NULL,
	/* ISBN, UPC */
	product_code_type ENUM('UPC', 'ASIN', 'ISBN'),
	/* Numerical representation */
	producr_code_value VARCHAR(256),
    category ENUM('Appliances', 'Automotives', 'Books', 'Clothing', 'Electronics',
                  'Home Goods', 'Sports And Outdoors', 'Tools', 'Toys', 'Other'),
	cost FLOAT NOT NULL,
	description TEXT NOT NULL, 	
	posted_date DATETIME NOT NULL,
    sold BOOLEAN NOT NULL,
    hits INT NOT NULL,
	PRIMARY KEY(id)
);

/* User accounts */
CREATE TABLE Customer 
(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR (32) NOT NULL,
    password VARCHAR (256) NOT NULL,
    first_name VARCHAR (32) NOT NULL,
    last_name VARCHAR (32) NOT NULL,
    zipcode INT NOT NULL,
    security_question INT NOT NULL,
    security_answer VARCHAR (255) NOT NULL,
    email VARCHAR(64) NOT NULL,
    PRIMARY KEY(id)
);
CREATE TABLE ItemPhoto
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    item_id INT NOT NULL,
    photo_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE,
    FOREIGN KEY (photo_id) REFERENCES Photo(id) ON DELETE CASCADE
);

CREATE TABLE ItemComment
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    item_id INT NOT NULL,
    comment_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE,
    FOREIGN KEY (comment_id) REFERENCES Comment(id) ON DELETE CASCADE
);


CREATE TABLE ItemCustomer
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    item_id INT NOT NULL,
    customer_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(id) ON DELETE CASCADE
);
