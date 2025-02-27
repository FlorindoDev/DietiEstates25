FROM dependacy:1.0.0 AS builder

WORKDIR /app/AccessService/src

COPY src .

WORKDIR /app/AccessService

COPY pom.xml .

WORKDIR /app

COPY pom-parent.xml pom.xml

RUN mvn clean package -X

FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot \
    && mkdir -p /app/libs/

#COPY --from=builder app/Dependacy/DAO/target/*.jar app/libs/
#COPY --from=builder app/Dependacy/DatabaseLib/target/*.jar app/libs/
#COPY --from=builder app/Dependacy/DietiEstateExceptions/target/*.jar app/libs/
#COPY --from=builder app/Dependacy/EmailSender/target/*.jar app/libs/
#COPY --from=builder app/Dependacy/Model/target/*.jar app/libs/
#COPY --from=builder app/Dependacy/Validator/target/*.jar app/libs/


COPY --from=builder app/AccessService/target/*.jar app/AccessService/

USER nonroot

WORKDIR /app/AccessService

#CMD ["java", "-cp", "/app/libs/*:/app/AccessService/*", "org.ac.MainApp.Main"]

CMD ["java", "-jar", "AccessMicroService-1.0.0.jar"]