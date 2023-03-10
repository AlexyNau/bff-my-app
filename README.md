# Read Me
This is a sample to solve **github issue** : https://github.com/spring-cloud/spring-cloud-openfeign/issues/814

This is a BFF API (Backend for Frontend) that use services of **com.example.api.my-api**.

# Getting Started

Start the api as a SpringBootApplication, with a JDK 19.

**Swagger url** : http://localhost:8091/api/swagger-ui/index.html


### Step to reproduce
Use this endpoint, and see the result in debug mode in **UserController.java** :

GET on http://localhost:8091/api/v1.0/users/?page=0&size=5&sort=lastName%2CASC
