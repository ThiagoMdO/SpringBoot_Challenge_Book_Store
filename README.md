# SpringBoot_Challenge_Book_Store

<h1 align="center" style="text-align: center; background-color: #000; border-radius: 10px">  
    <img src = "https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/assets/128644651/e6beeb04-dc84-4d21-9645-ee86d08e8874" style="margin-top: 10px; height: 300px; width: 300px ">
    <p style="text-shadow : 1px 1px 10px orange">Book Store</p>
</h1>

> ### The application is responsible for part of a system capable of managing the control of book sales in a Book Store and it is possible to receive feedback where customers can choose their level of satisfaction and create or edit a comment on an order.
>"
## 📚 Documentations

- >[General Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store)
- [Product Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-products)
- [Order Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-orders)
- [Feedback Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-feedback)

## General System Documentation

### 👨‍🍳 Available Microservices

#### *Product*

```bash
  /products
    GET     -> Get all products
    POST    -> Create a new Book

  /products/{id}
    GET     -> Get a product By Id
    PUT     -> Update a product By Id
    DELETE  -> Delete a product By Id 
```

#### *Order*
```bash
  /orders
    GET     -> Get all orders
    POST    -> Create a new Order

  /orders/{id}
    GET     -> Get a Order By Id
    PUT     -> Update a Order By Id
    POST    -> Cancel a Order By Id 
```
#### *Feedback*

```bash
  /feedbacks
    GET     -> Get all Feedbacks
    POST    -> Create a new Feedback

  /feedbacks/{id}
    GET     -> Get a Feedback By Id
    PUT     -> Update a Feedback By Id
    DELETE  -> Delete a Feedback By Id 
```

<br/>

## 🔨 Tools 
<div display="inline">
    <img align="center" alt="Java" height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" />
    <img align="center" alt="spring" height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" />
    <img align="center" alt="Mongo DB" height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-original.svg" /> 
    <img align="center" alt="PostMan" height="30" width="40" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postman/postman-original.svg" />
    <img align="center" alt="JUnit5" height="30" width="30" src="https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/assets/128644651/9b1a8dc4-f562-4474-8fa0-d19627efe0e7"/>
    <img align="center" alt="Mockito" height="30" width="55"  src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF5fhM2GwoPeFJAbuCcPFALX_aXaA_PBCDDRyFgLAyEA&s" style="border-radius: 20px"/>
    <img align="center" alt="Swagger" height="30" width="30"  src="https://static-00.iconduck.com/assets.00/swagger-icon-512x512-halz44im.png" style="border-radius: 20px"/>
</div>
<br/>

## Author 🧑🏼‍🎨

- [@ThiagoMdO](https://github.com/ThiagoMdO)

<br/>

 ## How use it ⚙️

 1 . Fork or download this project in this page
 
 2 . Ports 8080, 8081, 8082 and 27017 need to be free for other services and databases to run smoothly.

    2.1 . 8080  -> Microservice: Products 

    2.2 . 8081  -> Microservice: Orders

    2.3 . 8082  -> Microservice: Feedback

    2.4 . 27017 -> MongoDB

3 . To use libraries and tools that support Jakarta EE, such as Swagger in version 3 and others, you may need to use at least JDK 17, which includes transitioning packages from javax.* to jakarta.*. However, JDK 17 is not strictly required for all dependencies and plugins.
**It is recommended to use Java version 17 or higher**.

4 . You must have Mongodb installed on your machine and use port 27017 (default).

5 . Run all microservices

6 . Open the Swagger documentation at the addresses, and test the endpoints:
<br/>
**Instructions for each microservice will be in each README of each microservice folder**
    
```bash
    #Products
    /http://localhost:8080/swagger-ui/index.html/

    #Orders
    /http://localhost:8081/swagger-ui/index.html/

    #Feedbacks
    /http://localhost:8082/swagger-ui/index.html/
```
## Author 🧑🏼‍🎨

- [@ThiagoMdO](https://github.com/ThiagoMdO)

More information in my [Portfolio](https://thiagomdo.github.io/Site_Portfolio/)
