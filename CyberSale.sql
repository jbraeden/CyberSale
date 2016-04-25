/* 
 * Created by Patrick Abod on 2016.04.07  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
/**
 * Author:  patrickabod
 * Created: Apr 7, 2016
 */

DROP TABLE IF EXISTS ItemPhoto;
DROP TABLE IF EXISTS CustomerItem;
DROP TABLE IF EXISTS Photo;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Customer;

CREATE TABLE Photo
(
    id INT NOT NULL AUTO_INCREMENT,
    file_name VARCHAR (256) NOT NULL,
    PRIMARY KEY(id)
);

/* Items up for sale*/
CREATE TABLE Item 
(
	id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(256) NOT NULL,
	/* ISBN, UPC */
	product_code_type VARCHAR(5),
	/* Numerical representation */
	product_code_value VARCHAR(256),
    category VARCHAR(64) NOT NULL,
	cost DOUBLE NOT NULL,
	description TEXT NOT NULL, 	
	posted_date DATETIME NOT NULL,
    sold BOOLEAN NOT NULL,
    hits INT NOT NULL,
    zipcode INT NOT NULL,
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
    phone_number VARCHAR(15),
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


CREATE TABLE CustomerItem
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    item_id INT NOT NULL,
    customer_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(id) ON DELETE CASCADE
);