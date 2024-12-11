#!/bin/bash

# Ensure script exits on error
set -e

# Navigate to the /mnt directory
cd /mnt

# Run the application JAR file
java -jar target/cart-0.0.1-SNAPSHOT.jar
