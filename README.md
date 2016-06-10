# Spring Boot

> My notes from [Spring Boot](https://app.pluralsight.com/library/courses/spring-boot-first-application/description) course on Pluralsight.

## Demo: Creating a Spring Boot App

Prerequisites:

- Java 1.8
- Maven
- IDE (Spring STS, IntelliJ)

With Spring Boot, run plain old Java program (`public static void main...`) rather than using a container.

Add `@SpringBootApplication` to top of class to enable spring boot. This annotation scans project for Spring components, and auto-wires up most of Spring libraries by enabling auto-configuration.

To fire up Spring Boot

```java
public static void main( String[] args ) {
  SpringApplication.run(App.class, args);
}
```

Then simply run the app as a java application. This starts up Tomcat at [http://localhost:8080](http://localhost:8080).

## Learning Path for Spring Boot

[Spring Reference](http://spring.io)

* Spring Fundamentals
* Spring Security Fundamentals
* Spring with JPA nad Hibernate
* Getting Started with Spring Data JPA/REST

## Spring Boot Dependency Management

Intelligent collection of dependencies are called: BOM - bill of materials.

Matches up which versions of libraries/frameworks work well with each other. Example spring-core 4.2.3 works well with logback-core 1.1.3.

How does BOM work? Starts with `<parent>` dependency in pom.xml:

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>1.3.1.RELEASE</version>
</parent>
```
