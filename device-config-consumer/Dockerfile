FROM maven:3.9.16-eclipse-temurin-26 AS builder
WORKDIR /app
COPY pom.xml .

RUN mkdir -p ./device-config-consumer/src

COPY device-config-consumer/pom.xml ./device-config-consumer
RUN --mount=type=cache,target=/root/.m2 \
    cd device-config-consumer && \
    mvn dependency:go-offline

COPY device-config-consumer/src ./device-config-consumer/src
RUN --mount=type=cache,target=/root/.m2 \
    cd device-config-consumer && \
    mvn package -DskipTests

FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=builder /app/device-config-consumer/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]