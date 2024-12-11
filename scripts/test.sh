#!/bin/bash

# Ensure script exits on error
set -e

# Run tests using Maven
mvn test

echo "All tests passed successfully."