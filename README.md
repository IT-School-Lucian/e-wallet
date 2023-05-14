# E-Wallet

E-Wallet is a Java-based web application that enables users to perform various financial transactions,
including account creation, bank account setup in multiple currencies, fund transfer between personal accounts,
money transfer to other user accounts and bill payment, all with automatic currency conversion. The application also
includes user and admin roles.

---

## Technologies Used

* Java 20
* JDK 20
* Maven
* Spring Boot
* Spring JPA
* Spring Security
* Spring Web
* MySQL
* Lombok
* Thymeleaf

This project uses CircleCI for continuous integration. The build status is indicated by the badge below:

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/IT-School-Lucian/e-wallet/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/IT-School-Lucian/e-wallet/tree/master)

---

## Features

- <b> Registration and login: </b> Users can create an account and login to access the application's features.


- <b> Bank account creation: </b> Users can create bank accounts in multiple currencies.


- <b> Personal account transfers: </b> Users can transfer money between their own bank accounts.


- <b> Transfers to other users: </b> Users can transfer money to another user's bank account.


- <b> Bill payments: </b> Users can pay bills.


- <b> Automatic currency conversion: </b> The application automatically converts currency as needed during personal account transfers, transfers to other users, and bill payments.


- <b> User and admin roles: </b> The application includes user and admin roles, with admin access to user management.


- <b> User-friendly interface: </b> The application has a user-friendly interface for easy navigation and task completion.

---

## Running the Application

Clone the repository to your local machine.

Set up a MySQL database and configure the connection settings in the `application.properties` file.

Run the `main` method from the class `EWalletApplication`.

Open a web browser and go to http://localhost:8080 to access the application.

---