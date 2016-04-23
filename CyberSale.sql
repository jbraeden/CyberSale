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

INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\appletv.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\bag.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\car.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\couch.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\desk.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\snowboard.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\table.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\tv.jpg');
INSERT INTO `CyberSaleDB`.`Photo` (`file_path`) VALUES ('C:\\Users\\Asper\\ItemPhotos\\xbox.jpg');

INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Mac Mini', '200', '4 GB RAM ‑ 500 GB HDD ‑ 1.4 GHz Core', '2016-04-23', '0', '3', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Nike Bag', '75', 'Brand New w/ Tag', '2016-04-22', '0', '2', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Chevy Cruze 2015', '18000', '10k miles', '2016-04-21', '0', '1', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Brown Couch', '100', 'small wear and tear', '2016-04-20', '0', '9', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Computer Desk', '90', 'fits in most standard college dorms', '2016-04-19', '0', '8', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Burton Snowboard package', '120', 'Bindings, Board, and bag all included', '2016-04-18', '0', '7', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Dinner Table', '50', 'Table and four chairs', '2016-04-17', '0', '6', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('50\" LCD TV', '499', 'Samsung. 3 HDMI ports. Wi-Fi', '2016-04-16', '0', '5', '24060');
INSERT INTO `CyberSaleDB`.`Item` (`item_name`, `cost`, `description`, `posted_date`, `sold`, `hits`, `zipcode`) VALUES ('Xbox One w/ Controller', '350', 'Just the console and one controller', '2016-04-15', '0', '4', '24060');

INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('1', '1');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('2', '2');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('3', '3');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('4', '4');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('5', '5');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('6', '6');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('7', '7');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('8', '8');
INSERT INTO `CyberSaleDB`.`ItemPhoto` (`item_id`, `photo_id`) VALUES ('9', '9');
