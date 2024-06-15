# Market Data Application

This application is a RESTful API for market data with WebSocket support.

## Technologies Used

- Spring Boot 3.3.0
- PostgreSQL
- WebSocket
- DTO objects
- Clean Architecture
- Dependency Injection
- log4j2
- Flyway for database migrations
- Cron jobs

## How to Run

1. Ensure you have PostgreSQL running and create a database named `marketdata`.
2. Update the `application.yml` file with your PostgreSQL credentials.
3. Run the application:

```bash
./mvnw spring-boot:run
```

4. Initialize maven wrapper

```bash
mvn -N io.takari:maven:wrapper
```
5. Create `application.yml`

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://[host]:[port]/[db]
    username: [username]
    password: [password]
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: none
    open-in-view: false
  flyway:
    enabled: true
    locations: classpath:db/migration
```
