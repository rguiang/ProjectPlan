# Spring Boot "Project Plan" Console App

##Pre requisites
Java 11 Maven 4.0.0 PostGreSql running instance 

##Postgres Instance Configuration
In order to use your instance please update the Database Configuration section in `application.properties`

```sh
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/project_plan
spring.datasource.username=postgres
spring.datasource.password=P@ssw0rd
```

##How to build 
You can build the project by running tge following command:
```sh
mvn clean install
```

## How to Run
Open a command line (or terminal) and navigate to the folder where you have the project files. We can build and run the application by issuing the following command:

MacOS/Linux:
```sh
./mvnw spring-boot:run
```

Windows:
```sh
mvnw spring-boot:run
```

