<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Spring Boot](#spring-boot)
  - [First Spring Boot Project](#first-spring-boot-project)
    - [Demo: Creating a Spring Boot App](#demo-creating-a-spring-boot-app)
    - [Learning Path for Spring Boot](#learning-path-for-spring-boot)
    - [Spring Boot Dependency Management](#spring-boot-dependency-management)
    - [Other Spring Boot Initializers](#other-spring-boot-initializers)
    - [How Does Spring Boot Work?](#how-does-spring-boot-work)
    - [Why Move to Containerless Deployments?](#why-move-to-containerless-deployments)
  - [Creating Web Apps](#creating-web-apps)
    - [Demo: Creating REST Endpoints](#demo-creating-rest-endpoints)
    - [Spring MVC Integration Overview](#spring-mvc-integration-overview)
    - [Properties and Environmental Configuration](#properties-and-environmental-configuration)
    - [Properties Examples](#properties-examples)
  - [Configuring and Accessing a Data Source](#configuring-and-accessing-a-data-source)
    - [Identifying Frameworks for Integration](#identifying-frameworks-for-integration)
    - [DemoL Provisioning and INtegrating a Database](#demol-provisioning-and-integrating-a-database)
    - [DataSource configuration](#datasource-configuration)
    - [Demo: FlywayDB](#demo-flywaydb)
    - [Spring Boot Java Configuration](#spring-boot-java-configuration)
    - [Demo: Defining Multiple Datasources](#demo-defining-multiple-datasources)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Spring Boot

> My notes from [Spring Boot](https://app.pluralsight.com/library/courses/spring-boot-first-application/description) course on Pluralsight.

## First Spring Boot Project

### Demo: Creating a Spring Boot App

Prerequisites:

- Java 1.8
- Maven
- IDE (Spring STS, IntelliJ)

In this demo, will manually scaffold a spring boot app. Start a new Maven project in IDE, then pick Maven archetype "Quickstart".

With Spring Boot, run plain old Java program (`public static void main...`) rather than using a container.

Add `@SpringBootApplication` to top of class to enable spring boot. This annotation scans project for Spring components, and auto-wires up most of Spring libraries by enabling auto-configuration.

To fire up Spring Boot

```java
public static void main( String[] args ) {
  SpringApplication.run(App.class, args);
}
```

Then simply run the app as a java application. This starts up Tomcat at [http://localhost:8080](http://localhost:8080).

### Learning Path for Spring Boot

[Spring Reference](http://spring.io)

Other courses:

* Spring Fundamentals
* Spring Security Fundamentals
* Spring with JPA nad Hibernate
* Getting Started with Spring Data JPA/REST

### Spring Boot Dependency Management

Intelligent collection of dependencies are called: BOM - bill of materials.

Matches up which versions of libraries/frameworks work well with each other. Example spring-core 4.2.3 works well with logback-core 1.1.3.

How does BOM work? Starts with `<parent>` dependency in pom.xml, which brings in all the dependency management.

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>1.3.1.RELEASE</version>
</parent>
```

Starter parent also sets up other defaults such as plugins, min java version, resource filtering, etc.
Parent declaration does a lot of heavy lifting.

### Other Spring Boot Initializers

Besides manually scaffolding a spring boot app, there are several other options:

[Web Initializer](http://start.spring.io)  Switch to full version to see all libraries and frameworks that you can integrate with your project.

[Spring boot CLI](http://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html#getting-started-homebrew-cli-installation) - same back end api that web initializer uses.

To verify its working `$ spring --version`.

To create a new application `$ spring init --dependencies=web myNewApp`.

[Clone an example](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples) But they usually only show one integration with one framework.

### How Does Spring Boot Work?

First, application is started from Java main class.

Next, Spring Boot initializes the spring context that comprises your spring app, and honours any auto-config initializers, configuration and annotations that direct how to initialize and start up the spring context.

Last, an embedded server container is started and auto-configured. This is why no need for web.xml. Tomcat is the default servlet container but it can be swapped out with Jetty or change config options such as server port.

![How Spring Boot Works](images/how-does-spring-boot-work.png "How Spring Boot Works")

`@SpringBootApplication` under the covers sets up other annotations so you don't need to write them all out by hand, they are:

`@Configuration`: Classifies this java class as a configuration for the spring context that spring boot and spring use to initialize and configure components and environment settings used by Spring. This is equivalent to the older xml based spring configurations.

`@EnableAutoConfiguration`: Spring-boot specific annotation. If it finds any spring sub-projects or other spring-compatible frameworks on the classpath, it should attempt to auto-configure them and get them wired up and integrated automatically with Spring. This does a lot of the "magic" of Spring boot, which makes setting up a full Java stack app easy.

`@ComponentScan`: Tells spring boot and spring to look for any spring components such as controllers, services, repositories or any other spring components starting in the main app package, and recursively looking through all sub-packages from there. Recommended to keep main App class at top level of package, so everything else in project can be found by `@ComponentScan`.

Static `SpringApplication.run(...)` method initializes spring context, applies all annotation and configuration classes, searches for any spring components and places them in the spring context, and setsup and configures the embedded container.

### Why Move to Containerless Deployments?

With a container, need to:

* container needs to be setup and configured for each environment
* Need to use files like web.xml to tell container how to work
* Environment configuration is external to the app

With containerless:

* Runs anywhere Java is setup

## Creating Web Apps

### Demo: Creating REST Endpoints

Spring MVC to create RESTful endpoints. All we have to do is write a controller, [example](demo/src/main/java/com/boot/controller/ShipwreckController.java).

Behind the scenes, spring boot is handling the integration of spring mvc, then it sets up jackson/json library so that shipwreck info sent across http is automatically marshalled into and out of the Shipwreck.java model object.

### Spring MVC Integration Overview

Spring Boot automatically configured and integrate Spring MVC.

Adding spring-boot-starter-web in pom.xml does more than simply including spring-mvc in classpath. Since we also have `@EnableAutoConfiguration` enabled, spring boot also sets up some spring mvc features:

Sets up spring mvc view resolvers automatically. It sets up the content negotiating view resolver, which determines how to respond based off of content type. Since we're using JSON payloads, spring boot and spring mvc sets up the json-jackson library to handle content negotation views for application/json types.

Spring boot configured and told spring-mvc to serve static resources located at root of classpath in static, public, or resources path.

Spring boot sets up standard spring-mvc http message converters so it can use sensible defaults to convert json objects into java and vice versa. String encoding is set to UTF-8 out of the box.

Also sets up customizable hooks to give you full control over how spring-mvc is integrated.

### Properties and Environmental Configuration

To change application behaviour from defaults provided by Spring boot. This can be done with `application.properties`.

When application.properties file is placed at root of classpath (eg: `src/main/resources`), spring boot will load it and apply any of the property configurations to the application when it starts up.

Application properties can also be provided YAML format.

App properties can change from environment to environment, eg db credentials can be different in staging vs prod. To handle this, define additional properties file for each "profile" `application-{profile}.properties`. For example, `application-dev.properties`.

Profile-specific app properties can be loaded over the main app properties file and any env-specific properties will be overridden.

### Properties Examples

Logging, no more need for log4j.xml, can do with properties, for example, to set package org.springframework.web to debug log level:

```
logging.level.org.springframework.web=DEBUG
```

To customize port for servlet container (default is 8080):

```
server.port=8181
```

To specify a profile to load, in the IDE run configuration, add a VM argument:

```
-Dspring.profiles.active=test
```

See [Spring Boot Docs](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) for common appliation properties.

## Configuring and Accessing a Data Source

### Identifying Frameworks for Integration

To setup a persistence layer, need to add JPA and Spring Data JPA.
JPA ORM layer will talk to a database (will be using embeddable H2 database for this course, for simplicity).

Should also incorporate FlywayDB Migrations, for ability to version and migrate data structure along with code.

### DemoL Provisioning and INtegrating a Database

* H2 dependency
* Spring Boot Starter Data JPA (will detect H2 and auto configure/integrate)

By pulling in these dependencies, spring boot pulls in all the required transitive dependencies:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
</dependency>
```

Working with h2, enable console access via application.properties:

```
# Database
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```

Then retart app and access h2 at [http://localhost:8080/h2](http://localhost:8080/h2)

### DataSource configuration

Provide jdbc connection details via application.properties.

Spring boot will also try to automatically set up DataSource pooling. If spring detects any of the common pooling libraries on the classpath, the auto configuration will integrate that pool and tie it in with the data source.

`tomcat-jdbc` is default pooling strategy. Spring boot will also integrate with other pools.

Datasource properties:

```
spring.datasource.url=jdbc:h2:file:~/dasboot
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```

### Demo: FlywayDB

Steps:

* First add FlywayDB on classpath.
* Configure flyway DataSource.
* Create migration scripts.
* Migrate on app startup (no need to run seprate command)

Need to place flyway migration scripts on classpath. `resources/db/migration/V2__create_shipwreck.sql`. Name it V2 because flyway baselines at V1, so migrations start at V2.

Spring boot will configure flyway with the datasource.

Add this property to tell flyway if this is the first time migration is running, create the migrate metadata table so it can keep track of what version its on.

```
flyway.baseline-on-migrate=true
```

Also tell Hibernate (running as the JPA implementation), not to auto create any entities using the ddl of those entities:

```
spring.jpa.hibernate.ddl-auto=none
```

### Spring Boot Java Configuration

If you need to do some configuration that falls outside the auto configuration options. For example, to configure a datasource programmatically. This would be useful if you need to configure multiple data sources:

```java
@Configuration
public class PersistenceConfiguration {
  @Bean
  @ConfigurationProperties(prefix="spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }
}
```

Any configuration java class that has public methods with the `@Bean` annotation, will get loaded on startup and the return value of these methods will be set as beans inside the spring context. This allows you to configure any bean to the spring context.

### Demo: Defining Multiple Datasources
