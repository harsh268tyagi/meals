# Meal Recommendation Service

This is a Spring Boot microservice that recommends top healthy recipes and matching restaurants based on cuisine and city.

---

## Features

- Aggregates data from Documenu and Edamam APIs
- Provides offline mode with fallback JSON files
- Built with Spring Boot 3 and Java 17
- Swagger API documentation
- Dockerized & CI-ready (Jenkins)

---
## Design Patterns Used
- CREATIONAL
    - **Singleton**: For service beans
    - **Factory**: For creating API clients
    - **Builder**: For constructing objects

## How It Works

[Sequence Diagram](https://drive.google.com/file/d/1PnENvhUMW4ZAj2QBwx7NR2LXMLIjIHO7/view?usp=sharing)

1. User inputs Cuisine & City
2. Backend fetches:
    - Recipes from Edamam
    - Restaurants from Documenu
3. If offline mode is enabled or APIs fail → fallback data is served

---

## API Keys

Set the following in `application.yml`:
```yaml
documenu:
  api-key: YOUR_DOCUMENU_KEY
edamam:
  app_id: YOUR_APP_ID
  app-key: YOUR_APP_KEY
  account_User: YOUR_ACCOUNT_USER

```
## Running the Service
 ### Prerequisites
- Java 17
- Maven
- Docker (optional)
- Jenkins (optional)
- Postman (for testing)
- IntelliJ IDEA (or any Java IDE)
- Git
### Steps
1. VM Arguments:

`Pass the following VM arguments to your IDE or when running the encryption file.`
   ```bash
   -Djasypt.encryptor.password=Harsh_PS
   ```
``Add the encryption variables into the `application.yml` file for their respective places.

## DOCKER
### Build Docker Image
```bash
docker build -f src/main/java/com/assignment/meal/Dockerfile -t meal-app .
```
### Run Docker Container
```bash
docker run -p 8080:8080 meal-app
```



