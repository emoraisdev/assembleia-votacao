FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Configura o timezone para America/Sao_Paulo (UTC-3)
RUN ln -sf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    echo "America/Sao_Paulo" > /etc/timezone

WORKDIR /app

COPY . /app

RUN mvn package -DskipTests=true

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./unrandom", "-Duser.timezone=America/Sao_Paulo", "-jar", "/app/app.jar"]