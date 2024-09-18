# Use an official Gradle image to build the project
FROM gradle:8.2.1-jdk17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project to the working directory
COPY . .

# Build the project
RUN gradle build -x test

# Use an official OpenJDK runtime image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Define environment variable for JVM options if necessary
ENV JAVA_OPTS=""

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
