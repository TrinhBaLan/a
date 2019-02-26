create database my_classicmodels;
use my_classicmodels;
create table productlines(
	productLine varchar(50) not null,
    textDesciption varchar(4000),
    htmlDesciption mediumtext,
    image mediumblob,
    primary key (productLine)
);
create table products(
	productCode varchar(15) not null,
    productName varchar(70),
    productLine varchar(50),
    productScale varchar(10),
    productVendor varchar(50),
    productDesciption text,
    quantityInstock smallint(6),
    buyPrice double,
    primary key(productCode)
);
create table orders(
	orderNumber int(11) auto_increment not null,
    orderDate datetime,
    requiredDate datetime,
    shippedDate datetime,
    status varchar(15),
    comments text,
    customerNumber int(11),
    primary key (orderNumber)
);
create table orderdetails(
	orderNumber int(10) auto_increment,
    productCode varchar(15) not null,
    quantityOrdered int(11),
    priceEach double,
    orderLineNumber smallint(6),
    primary key(orderNumber, productCode)
);
use my_classicmodels;
alter table products add foreign key(productLine) references productlines(productLine) on update cascade;
alter table orderdetails add foreign key(orderNumber) references orders(orderNumber) on update cascade;
alter table orderdetails add foreign key(productCode) references products(productCode) on update cascade;

