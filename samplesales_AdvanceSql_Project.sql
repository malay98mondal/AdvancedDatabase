-- create database
create database SampleSales;

-- create table
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(255),
    product_country VARCHAR(255),
    category VARCHAR(255),
    price DOUBLE
);
-- create table
CREATE TABLE sales (
    sale_id INT PRIMARY KEY,
    product_id INT,
    sale_date DATE,
    sale_city VARCHAR(255),
    quantity INT,
    amount DOUBLE,
    customer_age INT,
    customer_gender VARCHAR(255),
    customer_nationality VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

select * from products;
select * from sales;
-- add products datapersons
INSERT INTO products (product_id, product_name, product_country, category, price) 
VALUES 
(1, 'Laptop X', 'USA', 'Electronics', 1200.0),
(2, 'Phone Y', 'China', 'Electronics', 800.0),
(3, 'Chair Z', 'Sweden', 'Furniture', 50.0),
(4, 'Book A', 'USA', 'Stationery', 10.0),
(5, 'TV B', 'Japan', 'Electronics', 1500.0),
(6, 'Table C', 'Germany', 'Furniture', 200.0),
(7, 'Headphones D', 'USA', 'Electronics', 100.0),
(8, 'Sofa E', 'Italy', 'Furniture', 400.0),
(9, 'Camera F', 'Japan', 'Electronics', 900.0),
(10, 'Lamp G', 'China', 'Home Decor', 30.0),
(11, 'Fridge H', 'USA', 'Appliances', 1200.0),
(12, 'Chair I', 'Italy', 'Furniture', 60.0),
(13, 'Pen J', 'Germany', 'Stationery', 1.0),
(14, 'Speaker K', 'USA', 'Electronics', 80.0),
(15, 'Bed L', 'Sweden','Furniture' ,700.0);

-- add sales data
INSERT INTO sales (sale_id, product_id, sale_date, sale_city, quantity, customer_age, customer_gender, customer_nationality) 
VALUES 
(1, 1, '2023-09-01', 'New York', 2, 30, 'Male', 'USA'),
(2, 2, '2023-09-05', 'Beijing', 5, 25, 'Female', 'China'),
(3, 3, '2023-09-10', 'Stockholm', 10, 35, 'Male', 'Sweden'),
(4, 4, '2023-09-15', 'Chicago', 20, 40, 'Female', 'USA'),
(5, 5, '2023-09-20', 'Tokyo', 3, 28, 'Male', 'Japan'),
(6, 6, '2023-09-25', 'Berlin', 6, 32, 'Female', 'Germany'),
(7, 7, '2023-09-30', 'Los Angeles', 4, 22, 'Male', 'USA'),
(8, 8, '2023-10-05', 'Rome', 8, 31,'Female','Italy'),
(9, 9,'2023-10-10','Shanghai',7 ,33,'Male','China'),
(10 ,10 ,'2023-10-15','Sydney' ,1 ,27 ,'Female' ,'Australia'),
(11 ,11 ,'2023-10-20','New Delhi' ,12 ,29 ,'Male' ,'India'),
(12 ,12 ,'2023-10-25','Paris' ,9 ,37 ,'Female' ,'France'),
(13 ,13 ,'2023-10-30','Mexico City' ,100 ,50 ,'Male' ,'Mexico'),
(14 ,14 ,'2023-11-05','Toronto' ,15 ,41 ,'Female' ,'Canada'),
(15 ,15 ,'2023-11-10','Istanbul' ,5 ,26 ,'Male' ,'Turkey');

-- for save

commit;

/*As for OLAP cubes or views */

CREATE VIEW sales_by_product_category AS 
SELECT p.category, SUM(s.quantity) as total_sales
FROM products p
JOIN sales s ON p.product_id = s.product_id
GROUP BY p.category;

-- show view
SELECT * FROM sales_by_product_category;

-- 1. Write a OLAP query to provide total sales by product_category and year.
SELECT p.category, YEAR(s.sale_date) as year, SUM(s.quantity * p.price) as total_sales
FROM sales s
JOIN products p ON s.product_id = p.product_id
GROUP BY p.category, YEAR(s.sale_date);

-- 2. Write a OLAP query to provide total sales by product_category, month and year. 
SELECT p.category, MONTH(s.sale_date) as month, YEAR(s.sale_date) as year, SUM(s.quantity * p.price) as total_sales
FROM sales s
JOIN products p ON s.product_id = p.product_id
GROUP BY p.category, MONTH(s.sale_date), YEAR(s.sale_date);

-- 3. Write a OLAP query to provide total sales by product_country, sales_city, and customer_nationality.
SELECT p.product_country, s.sale_city, s.customer_nationality, SUM(s.quantity * p.price) as total_sales
FROM sales s
JOIN products p ON s.product_id = p.product_id
GROUP BY p.product_country, s.sale_city, s.customer_nationality;

-- 4. Write a OLAP query to provide total sales by product_category, sale_city, and customer_age.
SELECT p.category, s.sale_city, s.customer_age, SUM(s.quantity * p.price) as total_sales
FROM sales s
JOIN products p ON s.product_id = p.product_id
GROUP BY p.category, s.sale_city, s.customer_age;

-- 5. Write a OLAP query to provide total sales by product_category, customer_age, customer_gender, customer_nationality.
SELECT p.category, s.customer_age, s.customer_gender, s.customer_nationality, SUM(s.quantity * p.price) as total_sales
FROM sales s
JOIN products p ON s.product_id = p.product_id
GROUP BY p.category, s.customer_age, s.customer_gender, s.customer_nationality;

-- 6. Write a OLAP query to provide total sales by product_country, sales_city, customer_nationality according to different customer_age

DELIMITER //
CREATE PROCEDURE get_SalesByAge(IN customer_age INT)
BEGIN
    SELECT p.product_country, s.sale_city, s.customer_nationality, s.customer_age, SUM(p.price*s.quantity) AS total_sales
    FROM sales s
    JOIN products p ON s.product_id = p.product_id
    WHERE s.customer_age = customer_age
    GROUP BY p.product_country, s.sale_city, s.customer_nationality, s.customer_age;
END //


CALL get_SalesByAge(30);



