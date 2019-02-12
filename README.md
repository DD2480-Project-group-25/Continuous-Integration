# Continuous-Integration
Continuous-Integration is the basis of a CI server.

It is developed as exercise of learning about working together on GitHub.

[Link to commit log server](http://157.230.31.10:8080/log/)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Your machine needs to have a recent version of Java and Gradle. We have tried OpenJDK 11.0.1 and Gradle 5.1.1. 

### Installing

To get a development environment you should just need to clone the repo.

```
cd $SOME_DIRECTORY
git clone git@github.com:DD2480-Project-group-25/Continuous-Integration.git
```

### Database with logged history

An HTTP post request is used to log history from the CI service runs. When running ```python3 manage.py runserver 8080``` standing in ```./logDb```, the API for posting will be available at ```http://localhost:8080/api/```.

Format for post request to the api:

```{"log entries":{"commit_id":"[github commit id]","start":"[timestamp]","status":"[status of build]","message":"[log message]"}}```

An example of a post request:

```{"log entries":{"commit_id":"abc123","start":"21:00:00","status":"SUCCESSFUL","message":"All tests passed."}}```

## Running the tests

To be able to run the tests you need to get a personal access token from GitHub.

You can then run the tests with:
```
env TOKEN=$YOUR_PERSONAL_ACCESS_TOKEN ./gradlew test
```

### Coding style tests

This repo adheres to the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

You can format the project to adhere to the style guide with:
```
./gradlew googleJavaFormat
```

You can verify that the project adheres to the style guide with:
```
./gradlew verifyGoogleJavaFormat
```

## Built With

* [Gradle](https://gradle.org/) - Build tool
* [Docker](https://www.docker.com) - Containerization
* [Django](https://www.djangoproject.com) - Web framework
 
## Contributing
- Alzahraa Salman
  - CloneJob, NotifyJob
- Helena Alinder
  - NotifyJob, TestJob, CleanupJob
- Veronica Hage
  - DB, Frontend, BuildJob
- Marcus Granström
  - Docker, DB, Frontend, WebHook server 
- Jesper Larsson
  - Event system
  
Note: The statistics show that some members have added/removed more lines of code than others.
This is due to project setup and auto-generated content, rather than unbalanced workload.

## Run with docker
1. Download the project
2. Update `docker-compose.yml` with your own git ''oauth token''
3. Run `docker-compose build` from the root of the project to build docker images
4. Run `docker-compose up` from the root to start the servers

The ci-server will default listen at 0.0.0.0:8000 and the log-server listens at 0.0.0.0:8080

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Implementation
To perform cloning, compilation, testing and notifications we use a mix of executing system commands, the Gradle tooling and HTTP requests.

Our architecture was designed so that each step of the process could, to some degree, be developed independently. Therefore areas that seem related (i.e. compilation and testing) used different technologies to perform their task.

Cloning and compilation uses Java's Runtime library to execute and monitor system commands. Testing instead relies on the Gradle tooling that allows it to directly incorporate Gradle into our program to run and monitor results of tests. Notifications, by nature, need to communicate with other systems using HTTP. The Java Standard Libaray has a HTTP clients that we use.

Unit testing these components is a challenge due to that they naturally interact with other systems such as the OS, file system or entierly other hosts. Our strategy to test these components boiled down to
1. try to set up the system we would interact with to some state,
2. run our code,
3. see if the outcome matched our expectations for the state we set up.

In this manner we did some negative tests, such as the one in [`TestTestJob.java`](./src/test/java/TestTestJob.java). We could have done more though. For example, we did not do positive tests in [`TestTestJob.java`](./src/test/java/TestTestJob.java), which we could have done by setting up a mock projects.
