## Description
Service for aggregation users data from multiple data sources, based on Spring boot

### API

#### /users
* `GET` : Find all users

#### Possible query params:

| Parameter | ParameterType | Description                            |
|-----------|---------------|----------------------------------------| 
| database  | @QueryParam   | specifying db to query users           |
| id        | @QueryParam   | specifying id of users to query        |
| username  | @QueryParam   | specifying username of users to query  |
| name      | @QueryParam   | specifying name of users to query      |
| surname   | @QueryParam   | specifying surname of users to query   |

#### Examples:
* url:port/users?database=postgres
* url:port/users?database=postgres&id=user_id&username=value&name=value&surname=value
* url:port/users?any=wrong&parameter=willbeignored - same as url:port/users

### Prerequisites and how to run 

Java, Maven, Docker, docker-compose

Application is docker-ready with provided docker-compose file

`mvn package -DskipTests`

`docker-compose up`

Don't forget to set up proper data-source urls for local run



