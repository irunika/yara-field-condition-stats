# Yara Atfarm Challenge (Irunika)

## Building the project

### Prerequisites

- Java 8 (tested with Corretto-8.212.04.2 openjdk v1.8.0_212)
- Maven 3 (tested with v3.5.4)
- Git

### Clone the project

```ssh
 git clone https://github.com/irunika/yara-field-condition-stats.git
 ```

### Build the project

```ssh
mvn clean install
```

## Running the application

```ssh
java -jar target/yara-field-condition-stats-1.0-SNAPSHOT.jar
```

## Swagger API Documentation

This project is shipped with Swagger definitions for the available APIs.
To access the swagger UI use `/swagger-ui.html`.  

If you are running the app in localhost and port 8080 click http://localhost:8080/swagger-ui.html

## H2 Database
H2 Database has been integrated to the app as an in memory database to test and run the 
application without any external database dependency.

*If external database need to be connected please follow the [Spring Boot - Working with SQL Databases](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html).* 

### Accessing H2 console

1. Use `/h2-console` to interact with the database. <br>
If you are running the app in localhost and port 8080 click http://localhost:8080/h2-console
2. Login to the console using <br> 
username: `sa`<br> 
password: ` ` (Empty)


## Assumptions

- More than one field condition record can be received per day.
- It is ok to record all field conditions received considering the analytical purposes.
- There is no reason to crate a relationship between the table `FIELD_CONDITION` 
and `FILED_CONDITION_DAILY_SUMMARY`.
