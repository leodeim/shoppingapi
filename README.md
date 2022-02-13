# Shopping API

Application is written in Java 11 using Spring Boot and PostgreSQL database.

## How to run

Easiest way to run this application is via Docker Compose. `Dockerfile` and `docker-compose.yml` files are prepared to build and run Java application and setup PostgreSQL database in docker containers.

> Before starting application, to change default settings, please check `.env` file: application port, database properties and test data seeding option could be configured there.
By default API port is set to **8080** and test data seeding on startup (ADD_TEST_DATA) is enabled.

To start application just run one of these commands in application directory:\
`docker-compose up` or `docker-compose up -d` to start it in the background.\
\
After build process two containers for API and DB will be started:\
<img src="/img/dockercontainers.png" width="450">

## How to access

By default **Swagger UI**, where all endpoints are described and could be tested, can be accessed by these URLs:\
<http://localhost:8080>\
<http://localhost:8080/swagger-ui/>\
\
**OpenAPI** documentation:\
<http://localhost:8080/v2/api-docs>

Swagger UI screenshot with API endpoints:\
<img src="/img/swagger.png" width="450">

## How it works

On application start 3 tables will be created in the database and by default some test data will be added:
- **products** table - 6 test products.
- **cart_rules** table - 3 default rules from task description.
- **cart_items** table - no initial data.

After adding one of the products from **products** table to the cart `POST /api/v1/cart/item`, calculations will be performed based on cart rules from **cart_rules** table 
and response with cart summary will be sent back.
```
{
  "limitReached": false, // is price limit reached? some rules could add such limit for the cart 
  "sum": 11.41, // overall price for all items in the cart without a discount
  "discount": 1.59 // discount calculated based on defined rules
}
```
New cart rules and products could be added via certain endpoints.

## Unit tests coverage

<img src="/img/coverage.png" width="550">
