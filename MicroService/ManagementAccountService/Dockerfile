FROM dependacy:1.0.0 AS builder

WORKDIR /app/ManagementAccountService/src

COPY src .

WORKDIR /app/ManagementAccountService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder app/ManagementAccountService/target/*.jar app/ManagementAccountService/

USER nonroot

WORKDIR /app/ManagementAccountService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "ManagementAccountService-1.0.0.jar"]