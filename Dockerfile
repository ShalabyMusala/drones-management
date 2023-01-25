FROM maven:3.6.3-jdk-8 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:19-alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

WORKDIR /opt/app

COPY --from=build /home/app/target/drones-management-1.0.0.jar app.jar


ENTRYPOINT ["java","-jar","app.jar"]