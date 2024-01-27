FROM gradle:8.5-jdk21-alpine as builder

ARG APP_NAME=unimart
ARG SNAPSHOT_VERSION=0.0.1-SNAPSHOT
ARG APP_NAME_FOR_JAR=UnimartBackendApplication
ARG JAR_NAME=$APP_NAME_FOR_JAR-$SNAPSHOT_VERSION
WORKDIR /app/unimart

COPY build.gradle /app
COPY build.gradle .

RUN gradle build --no-daemon
COPY src ./src

FROM openjdk:21-jdk-slim

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/unimart/target/UnimartBackendApplication-0.0.1-SNAPSHOT.jar .
EXPOSE 8000

CMD ["java", "-jar", "UnimartBackendApplication-0.0.1-SNAPSHOT.jar"]