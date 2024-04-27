# Order Microservice

<h1 align="center" style="text-align: center; background-color: #000; border-radius: 10px">  
    <img src = "https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/assets/128644651/4ffdf3c1-d692-4145-b378-898d634099eb" style="margin-top: 10px; height: 300px; width: 300px ">
    <p style="text-shadow : 1px 1px 10px orange">Order Microservice</p>
</h1>


The Order API allows you to register, display, edit and cancel orders following the business rules that are:

    • An order can only be canceled if the status is other than SENT.
    • An order cannot be canceled if it is more than 90 days old.
    • After cancellation, the order status must be changed to CANCELED.
    • It is possible to change the order status and address if it is not SENT or CANCELED


## 📚 Documentations

- [General Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store)
- [Product Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-products)
- >[Order Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-orders)
- [Feedback Documentation](https://github.com/ThiagoMdO/SpringBoot_Challenge_Book_Store/tree/main/ms-feedback)

<br/>

<h1>About 📑</h1>

### Fields
orders:

    o id: primary key, auto-increment
    o products: Order product list
    o address: Order delivery address
    o payment_method: Payment Method
    o subtotal_value: Subtotal order value
    o discount: Order discount
    o total_value: Total order value
    o created_date: Order creation date
    o status: Order status
    o cancel_reason: Reason for order cancellation
    o cancel_date: Order cancellation date

### All available endpoints

```bash
  /products
    GET     -> Get all orders
    POST    -> Create a new order

  /products/{id}
    GET     -> Get a order By Id
    PUT     -> Update a order By Id
    POST    -> Cancel a order By Id 
```

>>## Returns all items

```http
  GET /orders
```

>>## Create a new item

```http
  POST /orders
```

#### Payload Request to create
```bash
{
  "products": [
    {
      "id": "6616929eb63057298df31491",
      "quantity": 10
    },{
      "id": "6616929eb63057298df31492",
      "quantity": 10
    }
  ],
  "address": {
    "street": "One Street",
    "number": 153,
    "postalCode": "01001000"
  },
  "paymentMethod": "PIX"
}
```

#### Payload Response by created
```bash
{
    "id": "662d11a0746e910771c85acc",
    "products": [
        {
            "id": "6616929eb63057298df31491",
            "quantity": 10
        },
        {
            "id": "6616929eb63057298df31492",
            "quantity": 10
        }
    ],
    "address": {
        "street": "One Street",
        "number": 153,
        "complement": "lado ímpar",
        "city": "São Paulo",
        "state": "SP",
        "postalCode": "01001000"
    },
    "paymentMethod": "PIX",
    "subtotalValue": 959.0,
    "discount": 47.95,
    "totalValue": 911.05,
    "createdDate": "2024-04-27",
    "status": "CONFIRMED",
    "cancelReason": null,
    "cancelDate": null
}
```

>>## Returns a item

```http
  GET /orders/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id order | 6605903e1e2d5c55c2017225


>>## Update a item

```http
  PUT /orders/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id order | 6605903e1e2d5c55c2017225

#### Payload Request to update
```bash
{
  "status": "CONFIRMED",
  "address": {
    "street": "Two Street",
    "number": 120,
    "postalCode": "08588609"
  }
}
```

#### Payload Response by update
```bash
{
    "id": "6605903e1e2d5c55c2017225",
    "products": [
        {
            "id": "6616929eb63057298df31491",
            "quantity": 10
        },
        {
            "id": "6616929eb63057298df31492",
            "quantity": 10
        }
    ],
    "address": {
        "street": "Two Street",
        "number": 120,
        "complement": "",
        "city": "Itaquaquecetuba",
        "state": "SP",
        "postalCode": "08588-609"
    },
    "paymentMethod": "PIX",
    "subtotalValue": 1500.0,
    "discount": 75.0,
    "totalValue": 1425.0,
    "createdDate": "2024-04-27",
    "status": "CONFIRMED",
    "cancelReason": null,
    "cancelDate": null
}
```

>>## Cancel a item

```http
  POST /orders/{id}
```
| Parameter   | Type       | Description                           | Example |
| :---------- | :--------- | :---------------------------------- | :------------|
| `id` | `string` | **Required** .Id product | 6605903e1e2d5c55c2017225

#### Payload Request to cancel
```bash
{
  "cancelReason": "Any one reason"
}
```

#### Payload Response by cancel
```bash
{
    "id": "6605903e1e2d5c55c2017225",
    "products": [
        {
            "id": "6616929eb63057298df31491",
            "quantity": 10
        },
        {
            "id": "6616929eb63057298df31492",
            "quantity": 10
        }
    ],
    "address": {
        "street": "Two Street",
        "number": 0,
        "complement": "",
        "city": "Itaquaquecetuba",
        "state": "SP",
        "postalCode": "08588-609"
    },
    "paymentMethod": "PIX",
    "subtotalValue": 1500.0,
    "discount": 75.0,
    "totalValue": 1425.0,
    "createdDate": "2024-04-27",
    "status": "CANCELED",
    "cancelReason": "Any one reason",
    "cancelDate": "2024-04-27"
}
```
