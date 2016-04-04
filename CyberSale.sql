DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS UserPhoto;
DROP TABLE IF EXISTS ItemPhoto;
DROP TABLE IF EXISTS Sell;
DROP TABLE IF EXISTS Have;


/* User accounts */
CREATE TABLE Users 
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR (32) NOT NULL,
    password VARCHAR (256) NOT NULL,
    first_name VARCHAR (32) NOT NULL,
    last_name VARCHAR (32) NOT NULL,
    zipcode INT NOT NULL,
    security_question INT NOT NULL,
    security_answer VARCHAR (255) NOT NULL,
    email VARCHAR(64) NOT NULL,
    rating INT	
);

/* Comments on listed Items */
CREATE TABLE Comments 
(
	id INT NOT NULL AUTO_INCREMENT,
	val TEXT,
	PRIMARY KEY(id)
	
);

/* Items up for sale*/
CREATE TABLE Item 
(
	id INT NOT NULL AUTO_INCREMENT,
	/* ISBN, UPC */
	identifierType VARCHAR(200),
	/* Numerical representation */
	identifierValue INT,
	cost INT NOT NULL,
	description TEXT NOT NULL, 	
	posted DATE NOT NULL, 
	sold BOOLEAN NOT NULL, 
	PRIMARY KEY(id)
);

/* User profile pictures */
CREATE TABLE UserPhoto
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
       user_id INT,
       FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

/* Item identification pictures */
CREATE TABLE ItemPhoto
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
       item_id INT,
       FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE
);
 
 /* Below relations link tables together */ 
 
 /* Links Users to Items they 'sell' */
 CREATE TABLE Sell
(
	user_id INT NOT NULL,
	item_id INT NOT NULL, 
	PRIMARY KEY (user_id, item_id)
);

/* Links Items to Comments they 'have' */ 
 CREATE TABLE Have
(
	comment_id INT NOT NULL,
	item_id INT NOT NULL, 
	PRIMARY KEY (comment_id, item_id)
);

