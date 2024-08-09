
# JavaEE POS System

## Introduction

This project is a point-of-sale (POS) application developed using Jakarta EE, adhering to a structured multi-layer architecture that promotes a distinct separation of responsibilities. This design enhances the application's ease of maintenance, scalability, and testability. The system features a strong backend built with Jakarta EE, utilizes a MySQL database, and supports asynchronous client-server communication through AJAX.

## Technology Stack

- Backend Framework: Jakarta EE
- Database: MySQL
- Client-Side Communication: AJAX
- Database Connectivity: JNDI (Java Naming and Directory Interface)
- Logging Frameworks: slf4j and logback

## Architectural Design

The system adheres to a layered architecture consisting of the following layers:

**1.** Presentation Layer: Responsible for managing the user interface and client-side interactions.

**2.** Business Logic Layer: Contains the core business processes and logic.

**3.** Data Access Layer: Manages the interaction with the database, ensuring data persistence.

## Setup and Installation

## **Prerequisites**

Before setting up the project, ensure that the following tools are installed on your system:

- JDK 21

- A Jakarta EE-compatible application server (e.g., Tomcat, WildFly, GlassFish)

- MySQL Server

- Maven

## Logging

The system uses different logging levels to capture various types of events:

- DEBUG: Detailed information useful for debugging purposes.
 
- INFO: General runtime information about the application's progress.

- WARN: Potentially harmful situations that may not immediately cause an issue.

- ERROR: Error events that might still allow the application to continue running but indicate a problem.

## API Documentation

[API Documentation](https://documenter.getpostman.com/view/35385603/2sA3s1pCmM)

## License

This project is licensed under the MIT License - see the [MIT License](LICENSE) file for details.


