This project is a RESTful API implemented using Spring Boot for order pricing, including VAT calculation for each order and its items.
Features

    Calculates the total price and VAT for orders.
    Implements a database-backed product catalog using MySQL (or H2 for testing).
    Includes Docker support for containerized builds and deployment.

Running the Application

Prerequisites: 

Docker installed on your machine.

Building the Docker Image

Run the following command:

    docker build -t cart .

Running the Application

Run the container and bind it to port 9090:

    docker run -it --rm -p 9090:9090 cart

The API will now be accessible at http://localhost:9090.
Testing

To run tests within the Docker container:

    docker run -it --rm cart scripts/test.sh
