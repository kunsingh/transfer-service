
# Money Transfer application
Money Transfer service supporting concurrent calls

Application starts webserver on http://localhost:8080 by default

 - **Jetty** - as a server layer
 - **Jersey** - as a JAX-RS implementation
 - **JUnit 5** - as a unit test framework
 - **Rest Assured** - for API tests

Application may be started from standalone jar:
```sh
java -jar transfer-service-1.0-SNAPSHOT-jar-with-dependencies.jar
```
or as a maven goal

```sh
mvn exec:java
```
## Account API - `/accounts`

**GET** - retrieves all accounts from database

Response:
**Status: 200 OK**
```javascript
[
    {
        "id": 0,
        "balance": {
            "amount": 11000,
            "currencyType": "DOLLAR"
        }
    },
    {
        "id": 1,
        "balance": {
            "amount": 10000,
            "currencyType": "DOLLAR"
        }
    },
    {
        "id": 2,
        "balance": {
            "amount": 10500,
            "currencyType": "DOLLAR"
        }
    }
]
```
---
**POST** - persists new account 
**Request Body** - Account object

Sample request:
```javascript
{
        "balance": {
            "amount": 10500,
            "currencyType": "DOLLAR"
        }
 }
```

Sample response:
**Status: 200 OK**
```javascript
{
    "id": 0,
    "balance": {
        "amount": 10500,
        "currencyType": "DOLLAR"
    }
}
```
**/{id}** - account id
**GET** - retrieves accounts from database
## Account GET API - `/accounts/0`
Response:
**Status: 200 OK**
```javascript
{
    "id": 0,
    "balance": {
        "amount": 10500,
        "currencyType": "DOLLAR"
    }
}
```
Account doesn't exist:
**Status: 204 No Content**

## Transaction API - `/transfer`

**POST** - submit new transaction

**Request Body** - MoneyTransfer object

Sample request:
```javascript
{

"sourceAccountNumber": 1,
"targetAccountNumber": 0,
"amount": "500"

}
```

Sample response:
**Status: 200 OK**
```javascript
{
    "id": 0,
    "sourceAccountNumber": 1,
    "targetAccountNumber": 0,
    "amount": 500,
    "success": true,
    "transactionNote": "Transaction successfully completed!"
}
```

Insufficient balance on source account:
**Status: 409 Conflict**
```javascript
Money Transfer cant be performed due to lack of funds on the account.
```

One of the party accounts doesn't exist:
**Status: 400 Bad Request**
```javascript
Account(s) doesnt exist. | Source: null, Target: Account{id=2, balance=10}
```
