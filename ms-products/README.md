# Product Microservice

<h1 align="center" style="text-align: center; background-color: #000; border-radius: 10px">  
    <img src = "https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/assets/128644651/ac4e6bb3-c070-4f6f-ac3d-52a6a65b4895" style="margin-top: 10px; height: 300px; width: 300px ">
    <p style="text-shadow : 1px 1px 10px orange">Product Microservice</p>
</h1>


The product API allows you to register, display, edit and delete books following the business rules that are:

    • The product name must be unique.
    • The product description must have at least 10 characters.
    • The value of the product must be a positive number.


## 📚 Documentations

- [Documentação Geral](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store)
- >[Documentação Product](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-products)
- [Documentação Order](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-orders)
- [Documentação Feedback](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-feedback)


<br/>

<h1>About 📗</h1>

### Fields
products:

    o id: primary key, auto-increment
    o name: product name
    o value: product value
    o description: produc description

### All available endpoints

```bash
  /products
    GET     -> Get all products
    POST    -> Create a new Book

  /products/{id}
    GET     -> Get a product By Id
    PUT     -> Update a product By Id
    DELETE  -> Delete a product By Id 
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id product | 6616929eb63057298df31491

#### Payload Request
```bash
{
    "name": "Product name",
    "description": "Product description",
    "value": 10.5
}
```

#### Payload Response
```bash
{
    "id": 6616929eb63057298df31491,
    "name": "Product name",
    "description": "Product description",
    "value": 10.5
}
```

>>## Returns all items

```http
  GET /products
```

>>## Create a new item

```http
  POST /products
```

#### Payload Request

```bash
{
    "name": "Product name",
    "description": "Product description",
    "value": 10.5
}
```

>>## Returns a item

```http
  GET /products/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id product | 6616929eb63057298df31491


>>## Update a item

```http
  PUT /products/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id product | 6616929eb63057298df31491

#### Payload Request
```bash
{
    "name": "Product name2",
    "description": "Product description2",
    "value": 50.99
}
```

>>## Delete a item

```http
  DELETE /products/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id product | 6616929eb63057298df31491
