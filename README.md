# Notification Test

We have to create a notification system that has the ability to receive a message and depending on
the category and subscribers, notify these users in the channels they are registered.

It will be 3 _message categories_:
* Sports
* Finance
* Movies

And there will be 3 _types of notification_, each type should have its own class to manage the logic of
sending the message independently.
* SMS
* E-Mail
* Push Notification

No notification will actually be sent or the need to communicate with any external APIs, only will 
register the notification in an archive of Logs or in a database.

In the log, it will need to save all the information necessary to identify that the notification has been
sent correctly to the respective subscriber, such as the type of message, type of notification, user
data, time, etc.

No user administration is required, you can use a Mock of users in the source code, and they must have
the following information:
* ID
* Name
* Email
* Phone number
* Subscribed [ ] here you need to list all the categories where the user is subscribed
* Channels [ ] a list of the notification's channels (SMS | E-Mail | Push Notification)

As user interface you need to display 2 main elements.
* Submission form. A simple form to send the message, which will have 2 fields:
  + Category: List of available categories.
  + Message: Text area, only validate that the message is not empty.
* Log history. A list of all records in the log, sorted from newest to oldest

## Software Versions

* Java 17 (Built with [Temurin java](https://adoptium.net/temurin/releases/))
* Maven 3.9.4 (through [Spring Initializr](https://start.spring.io/))
* Spring Boot 3.1.3
* H2 Database 2.2.220
* Liquibase 4.23.0

## Database

An embedded H2 database will be used to store all the information from this project:
* Catalogs
* Notifications
* Users

### Data migrations / Seeders

All migrations are available as Liquibase changesets. 

#### Catalogs

The following catalogs have also been inserted:

* Message category (Sports, Finance, Movies)
* Notification type (SMS, E-Mail, Push Notification)
* Notification status (Pending, Sent, Failed)

#### Sample Users
An [SQL script](src/main/resources/db/changelog/users.sql) has been included as a Liquibase changeset to insert 
sample users, each one subscribed to different message categories

## Starting the project

The project uses Maven, so the [standard commands](https://docs.spring.io/spring-boot/docs/3.1.3/maven-plugin/reference/htmlsingle/#run)
apply:

```shell
$ mvn spring-boot:run
```

## Running Unit Tests

Tests have been grouped using tags, so the Integration Tests (IT) are separate from the Unit Tests (UT). 
The [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/) has been configured
to exclude the integration tests altogether. 

Use this command to run the UTs:

```shell
$ mvn clean verify
```