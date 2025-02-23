FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Imposta la cartella di lavoro
WORKDIR /app

# copio le dipendenze nel nel immagine
COPY . .
COPY pom-noparent.xml pom.xml


RUN mvn clean install -X

FROM eclipse-temurin:21-jdk-alpine

COPY --from=builder app/DAO/target/*.jar /lib/DAO/
COPY --from=builder app/DatabaseLib/target/*.jar /lib/DatabaseLib/
COPY --from=builder app/DietiEstateExceptions/target/*.jar /lib/DietiEstateExceptions/
COPY --from=builder app/EmailSender/target/*.jar /lib/EmailSender/
COPY --from=builder app/Model/target/*.jar /lib/Model/


