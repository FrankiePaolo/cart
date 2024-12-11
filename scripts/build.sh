#!/bin/bash

# Ensure script exits on error
set -e

# Build the project using Maven
mvn clean package -DskipTests

echo "Build complete. JAR file available in /mnt/target"