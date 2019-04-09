use classicmodels;
#cau1
select * from products where
productCode in (select productCode from orderdetails where 
orderNumber in (select orderNumber from orders where
month(orderDate) = 3 and year(orderDate) = 2005
));
#cau2
select distinct p.* from products as p
inner join orderdetails as od on p.productCode = od.productCode
inner join orders as o on od.orderNumber = o.orderNumber
where month(o.orderDate) = 3 and year(o.orderDate) = 2005;

#cau3
select * from orders,(select max(orderDate) as maxDate from orders ) as md where
year(orderDate) = year(md.maxDate) and month(orderDate) = month(md.maxDate);

#cau4
select o.*,
(select sum(quantityOrdered * priceEach) from orderdetails where orderNumber = o.orderNumber group by orderNumber ) as 'total'
from orders as o;

#cau6
select c.*,
(select sum(quantityOrdered * priceEach) 
from orders join orderdetails on orders.orderNumber = orderdetails.orderNumber  
where c.customerNumber = orders.customerNumber group by c.customerNumber) as 'Total price',
(select sum(amount) from payments as p 
where p.customerNumber = c.customerNumber group by customerNumber) as 'Paid'
from customers as c;