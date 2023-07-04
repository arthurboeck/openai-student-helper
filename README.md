# openai-student-helper

This is a small application that uses GPT-3.5 Turbo to help students to review academic contents. 
It was developed as an activity for the discipline of Java Software Architecture at the PUC-MG.

## Requirements

1. To use this application, you must have an [OpenAI account and have credits available to use](https://platform.openai.com/).
2. You must have Java Development Kit on version 17
    - You can check your version by running `java -version` in your terminal

## Usage

1. Clone this repository and open it in your IDE of choice
2. Open the configuration file located at [bootstrap.yml](./src/main/resources/bootstrap.yml) and replace the `"<YOUR-API-KEY>"` with your OpenAI token:
    
```yaml
service:
  open-ai:
    key: <YOUR-API-KEY>
```

3. Build the project using the command: `./gradlew build -x test`
4. Start the SpringBoot Aplication using the command: `./gradlew bootRun`
5. Open the browser and access the Swagger UI at `localhost:8080/swagger-ui.html`

## Structure

This project is divides in 3 main packages:
- Controller: Contains the REST Controllers, and how to use the service layer
- Service: Contains the business logic, and how to use the OpenAI API
- Infra: Contains the infrastructure logic, and how to conect to external services

## Group members

- Andrew Costa Silva - 1137978
- Arthur Guterres Boeck - 1470051
- Danielson Augusto - 1481701
- Diego Ribeiro Alvarenga Silva - 1200783
- Guilherme Bruno Rodrigues Silva - 1177412
- Leandro Molinari - 1453411
