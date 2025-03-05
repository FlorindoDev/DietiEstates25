FROM dependacy:1.0.0 AS builder

WORKDIR /app/AdsEstateService/src

COPY src .

WORKDIR /app/AdsEstateService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder app/AdsEstateService/target/*.jar app/AdsEstateService/

USER nonroot

WORKDIR /app/AdsEstateService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "AdsEstateService-1.0.0.jar"]