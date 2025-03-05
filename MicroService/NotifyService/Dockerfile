FROM dependacy:1.0.0 AS builder

WORKDIR /app/NotifyService/src

COPY src .

WORKDIR /app/NotifyService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder app/NotifyService/target/*.jar app/NotifyService/

USER nonroot

WORKDIR /app/NotifyService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "NotifyService-1.0.0.jar"]