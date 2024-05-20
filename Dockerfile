FROM maven:3-eclipse-temurin-17-alpine AS build
 COPY src /tmp/src
 COPY pom.xml /tmp
 RUN mvn -f /tmp/pom.xml clean package
 FROM eclipse-temurin:17-jdk-alpine
 COPY --from=build /tmp/target/*.jar app.jar
 EXPOSE 8080
 ENTRYPOINT ["java","-jar","app.jar"]
