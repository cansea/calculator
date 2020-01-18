# Claim calculator

Spring boot + JPA (database Postgresql) + Spring MVC + JSP

# Install postgresql database docker to local

$ docker search postgres
NAME                                         DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
postgres                                     The PostgreSQL object-relational database sy…   6519                [OK]

$ docker pull postgres

$ docker run -d --name pgdocker -p 5432:5432 postgres

$ docker exec -ti [db-container] bash

$ su postgres

$ psql

psql (11.2 (Debian 11.2-1.pgdg90+1))
Type "help" for help.
postgres=#

postgres=# create database calc;
CREATE DATABASE
postgres=# \l
                                 List of databases
   Name    |  Owner   | Encoding |  Collate   |   Ctype    |   Access privileges
-----------+----------+----------+------------+------------+-----------------------
 calc      | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
 postgres  | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
 template0 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
 template1 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
(4 rows)

postgres=# \c calc
You are now connected to database "calc" as user "postgres".
calc=#

# Import data by below script
-- Drop table
-- DROP TABLE public.sales;
CREATE TABLE public.sales (
	product_code varchar(50) NOT NULL,
	transaction_date date NOT NULL,
	sale_amount numeric(11,2) NOT NULL,
	CONSTRAINT sales_pk PRIMARY KEY (transaction_date, product_code)
);

INSERT INTO sales(product_code,transaction_date,sale_amount) values('A','2019-07-02',100);
INSERT INTO sales(product_code,transaction_date,sale_amount) values('A','2019-07-18',900);
INSERT INTO sales(product_code,transaction_date,sale_amount) values('B','2019-08-03',1200);
INSERT INTO sales(product_code,transaction_date,sale_amount) values('B','2019-08-20',1300);
INSERT INTO sales(product_code,transaction_date,sale_amount) values('C','2019-09-12',50);
INSERT INTO sales(product_code,transaction_date,sale_amount) values('C','2019-09-01',4000);


-- Permissions
ALTER TABLE public.sales OWNER TO postgres;
GRANT ALL ON TABLE public.sales TO postgres;

-- Drop table
-- DROP TABLE public.discount_tier;
CREATE TABLE public.discount_tier (
	tier_no int4 NOT NULL,
	min_value numeric(11,3) NOT NULL,
	max_value numeric(11,3) NULL,
	discount_rate numeric(11,3) NULL,
	CONSTRAINT discount_tier_pk PRIMARY KEY (tier_no)
);

-- Permissions
ALTER TABLE public.discount_tier OWNER TO postgres;
GRANT ALL ON TABLE public.discount_tier TO postgres;

INSERT INTO discount_tier(tier_no,min_value,max_value,discount_rate) values(1,0,1000,0.02);
INSERT INTO discount_tier(tier_no,min_value,max_value,discount_rate) values(2,1001,5000,0.05);
INSERT INTO discount_tier(tier_no,min_value,max_value,discount_rate) values(3,5001,null,0.075);

# Build the code and package with war for docker image

$ mvn clean package

# Create an application docker image

$> docker build -f Dockerfile -t claim-calc-springboot-docker .

# Run docker

$> docker run -t --name claim-calc-springboot-docker --link pgdocker:dbpg -p 9093:9093 claim-calc-springboot-docker

# Browser the application

URL: http://localhost:9093
