FROM maven:3.8.6-eclipse-temurin-17 AS builder

# Imposta la cartella di lavoro
WORKDIR /app

# copio le dipendenze nel nel immagine
COPY pom-dependacy.xml pom.xml

WORKDIR /app/Dependacy

COPY Dependacy .

WORKDIR /app


RUN mvn clean install -X -pl Dependacy

FROM eclipse-temurin:17-jdk-alpine

COPY --from=builder app/Dependacy/DAO/target/*.jar /lib/DAO/
COPY --from=builder app/Dependacy/DatabaseLib/target/*.jar /lib/DatabaseLib/
COPY --from=builder app/Dependacy/DietiEstateExceptions/target/*.jar /lib/DietiEstateExceptions/
COPY --from=builder app/Dependacy/EmailSender/target/*.jar /lib/EmailSender/
COPY --from=builder app/Dependacy/Model/target/*.jar /lib/Model/


