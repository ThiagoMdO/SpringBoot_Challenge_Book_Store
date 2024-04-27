# Feedback Microservice

<h1 align="center" style="text-align: center; background-color: #000; border-radius: 10px">  
    <img src = "https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/assets/128644651/6a67ae4f-afb2-4602-9326-b4fc97882cae" style="margin-top: 10px; height: 300px; width: 300px ">
    <p style="text-shadow : 1px 1px 10px orange">Feedback Microservice</p>
</h1>


>The Feedback API allows you to register, display, edit and delete Feedback following the business rules that are:

    • The permitted satisfaction levels are:
         VERY_DISSATISFIED, DISSATISFIED, NEUTRAL, SATISFIED, VERY_SATISFIED.
    • It is not permitted to leave feedback on orders with a CANCELED status.


## 📚 Documentations

- [Documentação Geral](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store)
- [Documentação Product](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-products)
- [Documentação Order](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-orders)
- >[Documentação Feedback](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-feedback)


<br/>

<h1>About 🤝</h1>

### Fields
feedbacks:

    o id: primary key, auto-increment
    o scale: Feedback note
    o comment: Feedback comment
    o order_id: Order ID (foreign key)

### All available endpoints

```bash
  /feedbacks
    GET     -> Get all feedbacks
    POST    -> Create a new feedback

  /feedbacks/{id}
    GET     -> Get a feedback By Id
    PUT     -> Update a feedback By Id
    DELETE  -> Delete a feedback By Id 
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id feedback | 6605903e1e2d5c55c2017111

#### Payload Request
```bash
{
  "scale": "VERY_DISSATISFIED",
  "comment": "I don't know",
  "orderId": "6605903e1e2d5c55c2017223"
}
```

#### Payload Response
```bash
{
    "id": "662d3d2b3fbee44e298d6fb6",
    "scale": "VERY_DISSATISFIED",
    "comment": "I don't know",
    "orderId": "6605903e1e2d5c55c2017223"
}
```

>>## Returns all items

```http
  GET /feedbacks
```

>>## Create a new item

```http
  POST /feedbacks
```

#### Payload Request

```bash
{
  "scale": "VERY_DISSATISFIED",
  "comment": "I don't know",
  "orderId": "6605903e1e2d5c55c2017223"
}
```

>>## Returns a item

```http
  GET /feedbacks/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id feedback | 6605903e1e2d5c55c2017111


>>## Update a item

```http
  PUT /feedbacks/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id feedback | 6605903e1e2d5c55c2017111

#### Payload Request
```bash
{
  "scale": "VERY_DISSATISFIED",
  "comment": "I don't know",
  "orderId": "6605903e1e2d5c55c2017223"
}
```

>>## Delete a item

```http
  DELETE /feedbacks/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id feedback | 6605903e1e2d5c55c2017111
