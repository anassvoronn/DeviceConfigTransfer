FROM maven:3.9.16-eclipse-temurin-26 AS builder
WORKDIR /app
COPY pom.xml .

RUN mkdir -p ./device-config-parser/src

COPY device-config-parser/pom.xml ./device-config-parser
RUN --mount=type=cache,target=/root/.m2 \
    cd device-config-parser && \
    mvn dependency:go-offline

COPY device-config-parser/src ./device-config-parser/src
RUN --mount=type=cache,target=/root/.m2 \
    cd device-config-parser && \
    mvn package -DskipTests

FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=builder /app/device-config-parser/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]