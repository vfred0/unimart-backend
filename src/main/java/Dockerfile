FROM gradle:jdk17 as build
EXPOSE 8080
WORKDIR /app

COPY . /app
RUN gradle clean build

FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["sh", "-c", "java -jar /usr/local/lib/app.jar --spring.datasource.url=$DB_URL --spring.datasource.username=$DB_USER --spring.datasource.password=$DB_PASSWD"]