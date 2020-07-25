# JobProject
Waitress assistance system simulator. Written in Java, connected to MySQL database using Hibernate. Main objective: allow waitress to manage clients' orders, alter menu of the restaurant, "print" receipts, manage tables placed in restaurant.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need java 11 installed, Gradle installed and some sort of MySQL Server with empty schema named: restaurant. Address and credentials to this instance can be set in [Hibernate configuration file](src/main/resources/db/hibernate.cfg.xml), also remember to provide those properties in [Liquibase properties](build.gradle).

### Installing

In order to run this JavaFX application you have to follow those steps:
1. Run command in order to build project in root folder:
```
gradlew build
```

2. Run command to start up the application:
```
gradlew run
```

Please note that during gradlew run command Liquibase creates all the neccessary tables in schema named "restaurant".
