FROM dependacy:1.0.0 AS builder

WORKDIR /app/AdminManagementService/src

COPY src .

WORKDIR /app/AdminManagementService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder app/AdminManagementService/target/*.jar app/AdminManagementService/

USER nonroot

WORKDIR /app/AdminManagementService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "AdminManagementService-1.0.0.jar"]