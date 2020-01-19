# Claim calculator

Spring boot + JPA (database Postgresql) + Spring MVC + JSP

## **Step 1:** Install postgresql database docker to local

### Search and pull postgresql docker image
```
$ docker search postgres
NAME                                         DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
postgres                                     The PostgreSQL object-relational database sy…   6519                [OK]

$ docker pull postgres
```

### Run postgresql docker
```
$ docker run -d --name pgdocker -p 5432:5432 postgres
```

### Create database called **calc**
```
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
20cd67b0b6b2        postgres            "docker-entrypoint.s…"   5 seconds ago       Up 4 seconds        0.0.0.0:5432->5432/tcp   pgdocker

$ docker exec -ti pgdocker bash
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
                                 List of databases
   Name    |  Owner   | Encoding |  Collate   |   Ctype    |   Access privileges
-----------+----------+----------+------------+------------+-----------------------
 calc      | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
 postgres  | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
 template0 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
 template1 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
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

## **Step 2:** Set up database connection in calculator application

Configuration file location: src/main/resources/application.properties
```
spring.datasource.url=jdbc:postgresql://192.168.0.11:5432/calc
spring.datasource.username=postgres
spring.datasource.password=postgres
````

**Note**: Local IP address is required for database connection in docker network

## **Step 3:** Build the code and package with war for docker image
```
$ mvn clean package
```

### Create an application docker image
```
$ docker build -f Dockerfile -t claim-calc-springboot-docker .
$ docker images
REPOSITORY                     TAG                 IMAGE ID            CREATED             SIZE
claim-calc-springboot-docker   latest              afc9f81e71bc        18 hours ago        191MB
postgres                       latest              ec5d6d5f5b34        3 weeks ago         394MB
tomcat                         8.5-alpine          8b8b1eb786b5        8 months ago        106MB
openjdk                        8-jdk-alpine        a3562aa0b991        8 months ago        105MB
```

### Run docker
```
$ docker run -t --name claim-calc-springboot-docker --link pgdocker:dbpg -p 9093:9093 claim-calc-springboot-docker
$ docker ps -a
CONTAINER ID        IMAGE                          COMMAND                  CREATED             STATUS              PORTS                              NAMES
1f00dea18843        claim-calc-springboot-docker   "sh -c 'java -Djava.…"   12 hours ago        Up 2 hours          8080/tcp, 0.0.0.0:9093->9093/tcp   claim-calc-springboot-docker
20cd67b0b6b2        postgres                       "docker-entrypoint.s…"   20 hours ago        Up 12 hours         0.0.0.0:5432->5432/tcp             pgdocker
```

### Browser the application

URL: [http://localhost:9093](http://localhost:9093/)

