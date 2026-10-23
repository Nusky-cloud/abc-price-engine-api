# REST API for AbcOnlineStore

This repository contains the REST API for AbcOnlineStore. You can fetch products and calculate prices based on number of specified quantities by using this REST API. Also there is a web front-end which uses this REST API.

* Web frontend        - https://github.com/Nusky-cloud/abc-online-store.git

# How to run?

## Requirements

For building and running the REST API you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.5](https://archive.apache.org/dist/maven/maven-3/3.5.0/binaries/)
- [PostgreSQL](https://www.postgresql.org/download/windows/) as DBMS  

**NOTE :** After extracting Apache Maven to a location, add the **C:\Program Files\apache-maven-3.5.0\bin** path to the **Path** variable in **environment variables**. Also add a user variable **JAVA_HOME** and set the value to JDK path **C:\Program Files\Java\jdk1.8.0_144**. Open a console and execute **mvn -version** and you should see below output. 
```shell
Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-04T01:09:06+05:30)
Maven home: C:\Installations\apache-maven-3.5.0-bin\apache-maven-3.5.0\bin\..
Java version: 1.8.0_144, vendor: Oracle Corporation
Java home: C:\Program Files\Java\jdk1.8.0_144\jre
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

## Running

- First, install [PostgreSQL](https://www.postgresql.org/download/windows/) and create a database with below mentioned details. Keep the password field empty.

* database name : abc-price-engine
* username : postgres
* password : 

- Secondly, clone this repository on your computer or download as a zip file.
```shell
git clone https://github.com/Nusky-cloud/abc-price-engine-api.git
```

- After that, open a console and change directory to project root and execute below command. This will build and run the project.

```shell
mvn spring-boot:run
```

- When the service is up & running, it will create the tables in the DB for the mentioned entities in the service. Once the tables have been created in DB, please execute the SQL query which is located in sample-data/sample-data.sql in order to populate data in DB. 

- After the service is up & running & the DB is populated with data, send a **GET** request to the URL via a REST API client (i.e. [Postman](https://www.postman.com/downloads/)) :- 

```shell
http://localhost:8080/price-engine
```
	
You will receive two products as response.

# API Information

- **GET** http://localhost:8080/price-engine <br>
This is used to fetch all products.

- **POST** http://localhost:8080/price-engine/calculatePrice <br>
This is used to calculate prices based on number of specified quantities. Request payload should be 
```shell 
[
	{
		"productId": 1,
		"quantitiesByType": {
								"UNIT": 10
							}
	},
	{
		"productId": 2,
		"quantitiesByType": {
								"CARTON": 1
							}
	}
]
```

Additionally, you can test this REST API via the web front-end. The repository link is given above.
