DROP TABLE IF EXISTS orderdetails;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS orderevents;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS productmongo;
DROP TABLE IF EXISTS customerhistorymongo;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS customerordermongo;
CREATE TABLE orders (
  id  VARCHAR(36) PRIMARY KEY ,
  customer_id   VARCHAR(20),
  status int
);

CREATE TABLE orderdetails (
  id SERIAL PRIMARY KEY ,
  order_id VARCHAR(36) NOT NULL,
  product_id   int NOT NULL,
  quantity int 
);

CREATE TABLE product (
  id int PRIMARY KEY,
  name  VARCHAR(20) NOT NULL,
  price int,
  instock INT,
  inorder INT,
  reorder INT,
  description VARCHAR(200) 
);
CREATE TABLE productmongo (
  id int PRIMARY KEY,
  detail VARCHAR(500)
 
);
CREATE TABLE customerhistorymongo (
  customer_id    VARCHAR(20) NOT NULL,
  name  VARCHAR(20) NOT NULL,
  description VARCHAR(200) ,
  price int,
  unitsInStock int
);

CREATE TABLE customerordermongo (
  customer_id    VARCHAR(20) NOT NULL PRIMARY KEY,
  detail VARCHAR(500)
);


CREATE TABLE orderevents (
  id  SERIAL  PRIMARY KEY,
  order_id  VARCHAR(36) NOT NULL,
  customer_id    VARCHAR(20) NOT NULL,
  product_id int,
  quantity int,
  orderconsumed boolean,
  emailconsumed boolean,
  inventoryconsumed boolean
  
);

CREATE TABLE customer(
 customer_id    VARCHAR(20) NOT NULL,
 email  VARCHAR(20) NOT NULL,
 phone char(12)
);

