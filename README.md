# HouseJoy

This is a project regarding the problem statement of your Project which was mentioned in SDE_Assignment.pdf


## Prerequisities

[SpringBoot](https://spring.io/projects/spring-boot)

[Mysql 5.7](https://dev.mysql.com/downloads/windows/installer/5.7.html) 

## Steps to Setup

**1. Clone the git Repository**
```bash
git clone git@github.com:dineshadigarla/housejoy.git
```
**2. Create the Database**

We need to create the database and the tables will be automatically created when we run the application.

```
create database users_database
```
**3. Change Mysql username and password in your application.properties**

+ change  `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/spring-boot-rest-api-tutorial-0.0.1-SNAPSHOT.jar

```
Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.


## API's documentation

I am mentioning API's documentation here as I have not integrate Swagger Hub in my project

The Following are the CRUD API

**1. Buildings**
```
GET /api/v1/buildings (Get the List Of Buildings)
POST /api/v1/buildings (Create the Building)
GET /api/v1/buildings/{id} (Get the building by ID)
PUT /api/v1/buildings/{id} (Update the Building)
DELETE /api/v1/buildings/{id} (Delete the building)
```

**2. Floors**
```
GET /api/v1/floors (Get the list of Floors)
POST /api/v1/buildings/{id}/floors (Creates the floor and maps the floor with the building id)
GET /api/v1/floors/{id} (Get the floor by Id)
GET /api/v1/buildings/{id}/floors (Gets the Floors based on buildingID)
PUT /api/v1/floors/{id} (updates the Floor)
DELETE /api/v1/floors/{id} (Deletes the floor)
```
**3. Blocks**
```
GET /api/v1/blocks (Get the list of Blocks)

POST /api/v1/floors/{id}/blocks (Creates the block and maps the block with the floor id)

GET /api/v1/blocks/{id} (Get the block by Id)

GET /api/v1/floors/{id}/blocks (Gets the Blocks based on floorID)

PUT /api/v1/block/{id} (updates the Block)

DELETE /api/v1/blocks/{id} (Deletes the Block)
```

**4. Desks**
```
GET /api/v1/desks (Get the list of Desks)

POST /api/v1/blocks/{id}/desks (Creates the desks and maps the desk with the block id)

GET /api/v1/desks/{id} (Get the desks by Id)

GET /api/v1/blocks/{id}/desks (Gets the Desks based on deskID)

PUT /api/v1/desks/{id} (updates the Desk)

DELETE /api/v1/desks/{id} (Deletes the Desk)
```

**5. Employees**
```
GET /api/v1/employees (Get the list of Employees)

POST /api/v1/desk/{id}/employees (Creates the employees and maps the employee with the desk id)

POST /api/v1/employees (Creates the employee without mapping the desk)
```

**6.Association/Dissociation**
```
GET /api/v1/buildings/{id}/associations (Get the Desks which are associated with this building)

GET /api/v1/buildings/{id}/nonassociations (Get the Desks which are not associated with this building)

GET /api/v1/floors/{id}/nonassociations (Get the Desks which are not associated with this Floor)

GET /api/v1/floors/{id}/associations (Get the Desks which are associated with this Floor)

GET /api/v1/blocks/{id}/nonassociations (Get the Blocks which are not associated with this building)

GET /api/v1/blocks/{id}/associations (Get the Blocks which are associated with this building)

GET /api/v1/desks/nonassociations (Get the Desks which are not associated)

GET /api/v1/desks/associations (Get the Desks which are associated)

GET /api/v1/employee/associations (Get the Employee which are associated)

GET /api/v1/employee/nonassociation (Get the Employee which are not associated)
```

## Contributing
Pull requests are welcome.

Please make sure to update tests as appropriate.