# Alarm API
REST API for alarm app, with send JSON as response and use JWT authentication based on Java/Spring. 
In that moment use h2 in memory database, in future that willl be change to MySQL or PostgreSQL. 

# Authentication
[Example use](https://github.com/Kamil-IT/clock-api/blob/master/src/test/java/com/clock/clockapi/controller/AuthController.http)

Request to get JWT token should by to ```/api/auth``` and have JSON body witch username and password.
Token is valid 30 minutes.

If you havent got accout you should send request to ```/api/newaccount``` witch JSON body witch username and password and email.

# How to use
All request shoud have header: 
 ```Authorization```  ```Bearer JWT_BEARER_TOKEN ```

Base URL ```/api/v1/```


