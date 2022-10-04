# reactive-microservice

This is a reactive microservices project made with Spring Webflux.

The goal of this API is to simulate a purchase order from a client, this order has some properties like amount and the product that the client wants to buy.
This is received by an order service, who is in charge of manage the transaction of money of the client, and call two others microservices to store information about the client.

In the file application.properties you can set the port you want to run the different services, also its data bases ports.

Consists on three services: 

  1. Order service: Was made with Spring Data JPA to store information about orders
  
  2. User service: Was made with the reactive driver for relational databases r2bc.
    -> Is in charge of process information about the user that is sending the purchase order
    -> Store information about transanctions made by a specific user
    -> Implements DTO's as the format for a request and response
    
  3. Product service: Was made with reactive MongoDB
   -> Manage and store product information(name, price, description, etc).
   
 For testing purposes, embedded MongoDB and H2 was used, feel free of use the local database you want to run the application(Specify the port of each one in application.properties
 
