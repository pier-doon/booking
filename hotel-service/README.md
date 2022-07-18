# Preparation for launch our app

***
## Dockerfile overview

```Dockerfile
FROM openjdk:11-jdk-slim
# Set the server's time zone for the container
ENV TZ=Europe/Berlin
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone# update sources
RUN apt-get update \
    
# You can install some packages
#RUN apt-get install -y curl

# run under a user. This makes the whole thing more secure
RUN groupadd normalgroup
RUN useradd -G normalgroup normaluser
USER normaluser:normalgroup
# run app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```
***

## Prepare .env file for our app.
Make sure all security sensitive variables we need to pass to our application/containers
present here.
Change all paths for file/folder on yours (for example mongo paths) 

Example of .env file:

```dotenv
# host ports
SERVER_HOST_PORT=25000
MONGO_HOST_PORT=26000# host paths to mount
MONGO_DATA_HOST_PATH="A:\love\mongodb\data"
MONGO_LOG_HOST_PATH="A:\love\mongodb\log"
MONGO_INITDB_SCRIPTS_HOST_PATH="A:\love\mongodb\initdb.d"# application
APP_NAME=love
NETWORK_NAME=love-network# mongodb
MONGO_AUTO_INDEX_CREATION=true
MONGO_ROOT_USERNAME=root
MONGO_ROOT_PASSWORD=root
MONGO_DB=love
MONGO_DB_USERNAME=user1
MONGO_DB_PASSWORD=user1
```
And do not push this file to git repository. Store it secure.
***

## How to set up mongo on any machine/ Raspberry PI

Create folder in any suitable place in your filesystem with structure below:  
├── data  
├── initdb.d  
└── log  
Where data folder will contain db data, folder log will store logs and initdb.d folder
will store scripts that executed on first mongo creation.

In initdb.d folder create file with name "create-user.sh" and paste the code below:

```jshelllanguage
#!/bin/bash
mongo -u "$MONGO_INITDB_ROOT_USERNAME" -p "$MONGO_INITDB_ROOT_PASSWORD" --authenticationDatabase "$rootAuthDatabase" "$MONGO_INITDB_DATABASE" --eval "db.createUser({ user: '$MONGO_DB_USERNAME', pwd: '$MONGO_DB_PASSWORD', roles: [{ role: 'dbOwner', db: '$MONGO_INITDB_DATABASE' }] })"  
```

Attributes that starts with $ will be taken from environment variables (.env file).
***

## Creating a docker network for our app

Before running our containers with docker-compose we should create docker network.
It allows us to use containers on different machines as if it will be deployed on the
same host machine.

```shell
docker network create -d bridge search-hotel-network

```
***

## Build docker-compose file with env file
To build our containers with variables we inject from .env file we should execute in terminal:

```shell
docker-compose --env-file "A:\love\.env" build
```
***

## Run docker-compose file with env file
To run our containers with variables we inject from .env file we should execute in terminal:

```shell
docker-compose --env-file "A:\love\.env" up -d
```
***

