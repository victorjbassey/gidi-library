# gidi-library

An application containing REST APIs for a small library. Operations supported include 
adding, updating, deleting, and lending books to users, and also searching for books in the library.

### Dependencies:
- Spring web
- Spring security
- Data jpa
- H2 in-memory database
- JWT
- GSON
- Swagger
- JUnit 5


To run the application on docker, you need to do the following:

- Create the image for the app using this command:

  `docker build -t gidi-library .`

  _This would create a base image that contains the application and its dependencies._

- Run the application using this command:

  `docker run -p 8080:8080 -t gidi-library`

---
Full documentation for interacting with the APIs is available on the endpoints below
- `/swagger-ui.html`
- `/v2/api-docs`

H2 Database is available at `http://localhost:8080/h2`

An example of the full route to interact with the api is given below:

`http://localhost:8080/api/v1/books`

To perform most of the api operations, you need to have the role of a librarian. 
Log in as a librarian with the following details:

- username: `librarian`
- password: `password`

_This would return a token which you have to include in the header before being allowed to visit secure endpoints_


```
####  ##   #  ######  ##  #    #   ##
#     ###  #    ##   #  #  #  #    ##
####  # ## #    ##   #  #   ##     ##
#     #  ###    ##   #  #   ##
####  #   ##  ###     ##    ##     ##
```
