# Java Crud API with Spring, Mongo and JWT authentication

Source code at: [GitHub](https://github.com/ldevai/spring-crud-nosql-api)


#### Project creation

The boilerplate was created with the help of [start.spring.io](http://start.spring.io) with the following configuration:

    Project: Maven Project
    Language: Java
    Packaging: Jar
    Java: 11
    Spring Boot: 2.4.4 (as of this tutorial)
    Dependencies:
    - Spring Web
    - Spring Security
    - Spring Data Mongo DB
    - Lombok

The JWT dependency has to be added manually:

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version>
    </dependency>

Lombok is optional, but it greatly helps reducing boilerplate code.


## Development environment setup

Requirements: **jdk11**, **docker**, **docker-container**

Create and start a mongodb instance with docker:

    docker-compose up -d db mongo-express

Open the console at http://localhost:8081 on your browser and create the databases **demo-db-dev** and **demo-db**


## Running

    ./mvnw spring-boot:run


## Building

    ./mvnw clean package


## Deploying
Once built, the apps with **dev** or **prod** profiles can be launched:

    docker-compose up --build -d app-dev
    docker-compose up --build -d app

Remove the *-d* flag to run them in the foreground.


## Testing

The URLs used in the tests below are pointing to port **8000** which refers to the application started without docker.
To test using the **dev** docker image, replace the ports to **8001**. For **prod**, use **8002**.

#### Register

    curl -H 'Content-Type: application/json' -d '{"name":"Test User","email":"user@test.com","password":"abc123"}' http://localhost:8000/api/auth/register

#### Login

    curl -H 'Content-Type: application/json' -d '{"email":"user@test.com","password":"abc123"}' http://localhost:8000/api/auth/login

If everything is working, and you are using Linux/MacOS/Cygwin or have access to a bash, the one-liner below can be useful to parse the token from the response:

    TOKEN=$(curl -H 'Content-Type: application/json' -d '{"email":"user@test.com","password":"abc123"}' http://localhost:8000/api/auth/login | python -c 'import json,sys;print(json.load(sys.stdin)["access_token"])')

If you have the [JQ tool](https://stedolan.github.io/jq/) installed, the slightly shorter command below could also be used:

    TOKEN=$(curl -H 'Content-Type: application/json' -d '{"email":"user@test.com","password":"abc123"}' http://localhost:8000/api/auth/login | jq -r .accessToken)
    echo $TOKEN
    curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles

Trying to create an article without Admin role should result in an 403 Forbidden error.

````
curl -H "Authorization: Bearer ${TOKEN}" -H 'Content-Type: application/json' -d '{"title":"Test Article","url":"test","content":"Content of full article"}' http://localhost:8000/api/articles
````

````
{"timestamp":"2021-04-02T20:07:45.382+00:00","status":403,"error":"Forbidden","message":"","path":"/api/articles"}
````


#### Change user role to Admin on Mongo Express console and login again.


<br />


### Articles API

#### Get articles

    curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles

#### Create article

    curl -H "Authorization: Bearer ${TOKEN}" -H 'Content-Type: application/json' -d '{"title":"Test Article","url":"test","content":"Content of full article"}' http://localhost:8000/api/articles 

#### Get first article

    curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles/test

#### Update Article

    ID=$(curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles | python -c 'import json,sys;print(json.load(sys.stdin)[0]["id"])')

Alternatively, with JQ:
    
    ID=$(curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles | jq -r .[0].id)

    echo $ID
    curl -X PUT -H "Authorization: Bearer ${TOKEN}" -H 'Content-Type: application/json' -d '{"id":'\"${ID}\"',"title":"Updated Test Article","url":"test","content":"Updated content of full article","in_home":true}' http://localhost:8000/api/articles

Check article after updated:

    curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles/test

#### Delete article

    ID=$(curl -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles | python -c 'import json,sys;print(json.load(sys.stdin)[0]["id"])')
    curl -X DELETE -H "Authorization: Bearer ${TOKEN}" http://localhost:8000/api/articles/${ID}
