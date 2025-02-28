FROM dependacy:1.0.0 AS builder

WORKDIR /app/AgentManagementService/src

COPY src .

WORKDIR /app/AgentManagementService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder app/AgentManagementService/target/*.jar app/AgentManagementService/

USER nonroot

WORKDIR /app/AgentManagementService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "AgentManagementService-1.0.0.jar"]