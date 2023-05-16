# Parking Management (Team 7)

### Stack:

- Java 17
- Spring Boot (Hibernate, Security)
- VueJs

### Technologies:

- Eureka
- API Gateway
- Load balancing
- Kafka
- JWT
- PostgreSQL
- Docker
- Resilient microservices pattern - Circuit Breaker in Booking service

### Services:

- Eureka - http://localhost:8761/
- User Management
- Booking
- Location             (Update API key in application.properties)
- Parking Management
- Analytics
- Payment
- Kafka - 9092

#### Swagger (OpenAPI) available for each service:

- http://localhost:{port}/swagger-ui/index.html#/
- Some services need jwt token for making requests - Use User Management first - (auth-controller register or
  authenticate endpoints to receive token) - copy access_token and paste it to Authentication field in Swagger

### Run:

- Docker compose `docker-compose up`
- Run Eureka
- Run all other services
- Run VueJs (install dependencies) - npm run serve

Contributors:

- Viktoriia Abakumova [abakumova](https://github.com/abakumova)
- Maksym Avramenko [PowerMaxZ](https://github.com/PowerMaxZ)
- Serhii Murashko [SimpleSquirrelz](https://github.com/SimpleSquirrelz)

University of Tartu
2023
