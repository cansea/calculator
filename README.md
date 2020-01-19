# Claim calculator

Spring boot + JPA (database Postgresql) + Spring MVC + JSP

## Install postgresql database docker to local

### Search and pull postgresql docker image
```
$ docker search postgres
$ docker pull postgres
```

### Run postgresql docker
```
$ docker run -d --name pgdocker -p 5432:5432 postgres
```

### Create database called **calc**
```
$ docker exec -ti [db-container] bash
$ su postgres
$ psql
psql (11.2 (Debian 11.2-1.pgdg90+1))
Type "help" for help.
postgres=#
postgres=# create database calc;
CREATE DATABASE
```

### List databases
```
postgres=# \l
```

### Connect to database **calc**
```
postgres=# \c calc
You are now connected to database "calc" as user "postgres".
calc=#
```

### Import data 

Import script file location: src/main/resources/import.sql

Use the script file to create tables and import data to presgresql database

## Set up database connection in calculator application

Configuration file location: src/main/resources/application.properties
```
spring.datasource.url=jdbc:postgresql://192.168.0.11:5432/calc
spring.datasource.username=postgres
spring.datasource.password=postgres
````

**Note**: Local IP address is required for database connection in docker network

# Build the code and package with war for docker image
```
$ mvn clean package
```

# Create an application docker image
```
$ docker build -f Dockerfile -t claim-calc-springboot-docker .
$ docker images
```

# Run docker
```
$ docker run -t --name claim-calc-springboot-docker --link pgdocker:dbpg -p 9093:9093 claim-calc-springboot-docker
$ docker ps -a
```

# Browser the application
```
URL: http://localhost:9093
```
