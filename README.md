# Microservice_Spring
It has a two services Login and Post and one server Eureka.
They were written using the Spring Boot, built with Gradle.
Two services are in MVC framework, The server used the EurekaServer library.


## Description


## Environment for Mac OS
Gradle 6.8.1  
Build time:   2021-01-22 13:20:08 UTC  
Revision:     31f14a87d93945024ab7a78de84102a3400fa5b2  
Kotlin:       1.4.20  
Groovy:       2.5.12  
Ant:          Apache Ant(TM) version 1.10.9 compiled on September 27 2020  
JVM:          11.0.10 (Amazon.com Inc. 11.0.10+9-LTS)  
OS:           Mac OS X 10.16 x86_64 = big ser 11.2.3  

## How to run on Mac OS
Open 3 terminals on /eureka_server, /post, /account directory.
$ export JAVA_HOME=`/usr/libexec/java_home -v 11`  
$  ./gradlew run  

### Reference: 
Chapter 3. Interprocess communication in a microservice architecture by Chris Richardson Summary: https://docs.google.com/document/d/1N33vQcTGWu5-eEx0bDJTBiYX1tEpW66OTTiSwwJhf_o/edit?usp=sharing
