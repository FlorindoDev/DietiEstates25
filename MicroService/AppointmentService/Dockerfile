FROM dependacy:1.0.0 AS builder

WORKDIR /app/AppointmentService/src

COPY src .

WORKDIR /app/AppointmentService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder app/AppointmentService/target/*.jar app/AppointmentService/

USER nonroot

WORKDIR /app/AppointmentService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "AppointmentService-1.0.0.jar"]