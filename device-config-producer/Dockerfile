FROM maven:3.9.16-eclipse-temurin-26 AS builder
WORKDIR /app
COPY pom.xml .

RUN mkdir -p ./device-config-producer/src

COPY device-config-producer/pom.xml ./device-config-producer
RUN --mount=type=cache,target=/root/.m2 \
    cd device-config-producer && \
    mvn dependency:go-offline

COPY device-config-producer/src ./device-config-producer/src
RUN --mount=type=cache,target=/root/.m2 \
    cd device-config-producer && \
    mvn package -DskipTests

FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=builder /app/device-config-producer/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]