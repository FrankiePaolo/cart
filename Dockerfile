# Use a lightweight JDK image
FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory to /mnt
WORKDIR /mnt

# Copy all files from the project directory to /mnt in the container
COPY . /mnt

# Expose the application port
EXPOSE 9090

# Default CMD to build the project (can be overridden in scripts)
CMD ["/mnt/scripts/build.sh"]