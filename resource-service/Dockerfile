#Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Install the application dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy in the source code
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

#Run stage
FROM eclipse-temurin:21-jre-alpine-3.21

WORKDIR /app

# Setup an app user so the container doesn't run as the root user
RUN adduser -D app
USER app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]