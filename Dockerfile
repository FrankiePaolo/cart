# Use a lightweight JDK image
FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

# Expose the application port
EXPOSE 9090