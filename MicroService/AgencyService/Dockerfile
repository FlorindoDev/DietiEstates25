FROM dependacy:1.0.0 AS builder

WORKDIR /app/AgencyService/src

COPY src .

WORKDIR /app/AgencyService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder app/AgencyService/target/*.jar app/AgencyService/

USER nonroot

WORKDIR /app/AgencyService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "AgencyService-1.0.0.jar"]