[Android app surce code](https://github.com/Kamil-IT/alarm-app)

[![<CircleCI>](https://circleci.com/gh/Kamil-IT/alarm-api/tree/master.svg?style=svg)](https://circleci.com/gh/Kamil-IT/alarm-api/tree/master)

# Alarm API
REST API for alarm app, which accept JSON for request payload and also send responses to JSON and use JWT authentication. 
Also use as database PostgreSQL in production and for tests h2 in memory database. Server is running on AWS in Docker container.

# Authentication
[Example use](https://github.com/Kamil-IT/clock-api/blob/master/src/test/java/com/clock/clockapi/controller/AuthController.http)

Request to get JWT token should by to ```/api/auth``` and have JSON body with username and password.
Token is valid 30 minutes.

If you havent got accout you should send request to ```/api/newaccount``` witch JSON body witch username and password and email.

# How to use
All request shoud have header: 
 ```Authorization```  ```Bearer JWT_BEARER_TOKEN ```

Base URL ```/api/v1/```

Full swager JSON documentation available: 
[v2](http://alarmrestapi.eu-central-1.elasticbeanstalk.com/v2/api-docs)
[v3](http://alarmrestapi.eu-central-1.elasticbeanstalk.com/v3/api-docs)

Full swager ui documentation available on http://alarmrestapi.eu-central-1.elasticbeanstalk.com/swagger-ui
