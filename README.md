# Spring Boot e-commerce Application

This project is an e-commerce platform developed with Spring Boot.
The application allows the management of products, users,and orders to facilitate online sales.
Users can browser the product catalog, add items to their cart, and place orders.
Administrators have the ability to manage products and orders to meet customers needs.

## Table of Contents

- [Prerequisites](#Prerequisites)
- [Setup](#Setup)
- [Running the Application](#Running the Application)
- [API Endpoints](#API Endpoints)
- [Technologies Used](#Technologies Used)


## Prerequisites

Before you start make sure you have the following installed on your computer

- Java 23
- Maven
- MySql
- Postman (optional, for API testing)

## Setup

1. Clone the repository:

```
git clone https://github.com/
cd e-commerce
```
2. Create a MySQL database:

```
CREATE DATABASE ecom_db
```
3. Configure the database connection

Open directory `src/main/resources/application.properties` and configure the following properties with your MySQL credentials:

```
spring.datasource.url=jdbc:mysql://localhost:3306/ecom_db
spring.datasource.username=<your_user>
spring.datasource.password=<your_user_password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
4. install dependencies and build the project:

```mvn clean install```
