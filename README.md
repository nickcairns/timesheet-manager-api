# Timesheet Manager Controller API

The Timesheet Manager Controller API serves as the backend for the Timesheet Manager web app. 
It has been developed using Spring Boot and is designed as a RESTful controller. The API exposes 
various endpoints with different levels of authentication and authorisation. Clients can 
send HTTP requests to perform predefined functions, including submitting and retrieving data 
from a PostgreSQL database.

Hibernate, the chosen JPA implementation, is used to map the application models to a PostgreSQL database, ensuring data persistence.

## TODO
* Implement HTTPS
* Add authorisation checks to admin restricted endpoints
* Add method to create initial admin user in terminal on first run
