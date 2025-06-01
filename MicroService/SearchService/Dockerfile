FROM dependacy:1.0.0 AS builder

WORKDIR /app/SearchService/src

COPY src .

WORKDIR /app/SearchService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/


COPY --from=builder /app/SearchService/target/*.jar /app/SearchService/

USER nonroot

WORKDIR /app/SearchService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["sh", "-c", "java -jar $(ls /app/SearchService/*.jar)"]
