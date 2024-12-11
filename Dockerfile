# Use a lightweight JDK image that works well with ByteBuddy and Mockito
FROM eclipse-temurin:17-jdk

# Install Maven
RUN apt-get update && apt-get install -y maven

