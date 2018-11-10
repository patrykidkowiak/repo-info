## REPO-INFO
REST service which return details of given Github repository.

## Build with:
* Java 8
* Spring Boot 2
* Maven

## Live demo:
Available on: 

http://git-api.s3-website.eu-central-1.amazonaws.com/

## API:

Pattern:
```
GET /api/v1/repo/{repositoryUser}/{repositoryName}
```

Sample request using live demo:
```
GET https://repository-info.herokuapp.com/api/v1/repo/patrykidkowiak/currency-manager
```
## Tests:
Implemented two types of test:

* unit test using JUnit

* end to end using Selenium and ChromeDriver

## Fronted:

[Here](https://github.com/patrykidkowiak/repo-info-angular) you can check frontend part of application.



