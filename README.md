# Continuous-Integration
Continuous-Integration is the basis of a CI server.

It is developed as exercise of learning about working together on GitHub.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You machine need to have a recent version of Java and Gradle. We have tried OpenJDK 11.0.1 and Gradle 5.1.1. 

### Installing

To get an dev env you should just need to clone the repo.

```
cd $SOME_DIRECTORY
git clone git@github.com:DD2480-Project-group-25/Continuous-Integration.git
```

## Running the tests

To be able to run the tests you need to get an personal access token from GitHub.

You can then run the tests with:
```
env TOKEN=$YOUR_PERSONAL_ACCESS_TOKEN ./gradlew test
```

### And coding style tests

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

* [Gradel](https://gradle.org/) - Build tool

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

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Implementation
TO BE FILLED OUT.