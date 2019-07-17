# Money Transfer API 1.0

This demo is about a REST API for money transfer.
This application runs with in-memory database and embedded jetty as a standalone program which doesn’t require a pre-installed container/server.

# Requirements

  - Java 8
  - Maven 3.5
  
# Build 
```sh
$ cd money-transfer
$ mvn clean install
```
# Run
```sh
$ mvn jetty:run
```

The server is now listening at http://localhost:8080

# APIs

| Endpoint | Method |Payload|Response Status Code/Message|Sample Payload|
| ------ | ------ | ------ |------ |------ |
| /accounts |GET ||200 + Existing Accounts||
| /transfers | POST | fromAccountId, toAccountId, amount|201, Fund Transfer Completed Successfully|{ "fromAccountId" : 321, "toAccountId" : 322, "amount" : 110 }|
| /transfers | GET||200 + Existing transfers||

### Tech Used
  - Java 8
  - Servlet-Api 3.0
  - Dalesbred JDBC Wrapper
  - HSQLDB 2.4.0
  - Jetty Plugin
  - GSON 2.8.5
  - Junit 4.12
  - Mockito 2.10.0
