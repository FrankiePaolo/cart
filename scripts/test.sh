#!/bin/bash

# Ensure script exits on error
set -e

# Navigate to the /mnt directory
cd /mnt

# Run tests using Maven
mvn test

echo "All tests passed successfully."