# Book-API
This is a Spring Boot REST API for Book to achieve the CRUD and Batching operations using MySQL DB.

## Tools Used
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework for developing web applications and microservices
* 	[IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=mac) - IDE to develop the SpringBoot application
* 	[Maven](https://maven.apache.org/) - Tool to build and manage projects
* 	[MySQL](https://downloads.mysql.com/archives/community) - Relational Database Management System(RDBMS)
* 	[Postman](https://www.getpostman.com/) - API testing tool

## Packages
### main
- `batch` — For batch processing
- `controller` — Contains REST controllers/endpoints
- `model` — For the representation of data model(entities)
- `repository` — To communicate with the database
- `application.properties` — Contains project's configuration details
### test
- `controller` — Contains unit and integration tests

## Dependencies
```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-batch</artifactId>
  </dependency>
  <dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.project-lombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest-library</artifactId>
    <version>2.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## MySql Configurations
- Create MySQL table in Terminal/MySQLWorkbench
```
CREATE TABLE `test`.`BOOK_DETAILS` (
  `ISBN_ID` INT NOT NULL,
  `BOOK_NAME` VARCHAR(45) NULL,
  `AUTHOR_NAME` VARCHAR(45) NULL,
  `PUBLICATION_NAME` VARCHAR(45) NULL,
  `YEAR_PUBLISHED` INT NULL,
  PRIMARY KEY (`ISBN_ID`)); 
```
- MySQL configuration in application.properties
```
server.port=8585
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.batch.jdbc.initialize-schema=ALWAYS
spring.batch.job.enabled=false
```

### Endpoints
Below are the endpoints to test the Book-API

| URL                                    | Method | Header                     | Purpose                       |
|----------------------------------------|--------|----------------------------|-------------------------------|
| `http://localhost:8585/CreateBook`     | POST   | `Content-Type: text/plain` | Create Book Details           |
| `http://localhost:8585/ReadBook/101`   | GET    | `Content-Type: text/plain` | Read Book Details             |
| `http://localhost:8585/UpdateBook/101` | PUT    | `Content-Type: text/plain` | Update Book Details           |
| `http://localhost:8585/DeleteBook/101` | DELETE | `Content-Type: text/plain` | Delete Book Details           |
| `http://localhost:8585/BatchBooks`     | POST   | `Content-Type: text/plain` | Create all the Books' Details |
| `http://localhost:8585/ReadAllBooks`   | GET    | `Content-Type: text/plain` | Read all the Books' Details   |
| `http://localhost:8585/DeleteAllBooks` | DELETE | `Content-Type: text/plain` | Delete all the Books' Details |

