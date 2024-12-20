This project is a RESTful API implemented using Spring Boot for a Purchase Cart Service, that includes pricing and VAT calculation for each order and its items.

Features

    Calculates the total price and VAT for orders.
    Implements a database-backed product catalog using MySQL (or H2 for testing), with caching for improved performance.
    Includes Docker support for containerized builds and deployment.

Running the Application:

Build the application image and start MySql in detached mode:

    docker compose up -d

Run each step:

    docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt mytest ./scripts/build.sh

    docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt mytest ./scripts/test.sh

Docker's internal DNS resolves the service name "mysql" to the correct container IP address within the same Docker network. That's why we include the --network flag in our run commands:

    docker run -v $(pwd):/mnt -p 9090:9090 --network=$(basename $(pwd))_default -w /mnt mytest ./scripts/run.sh

The application is initialized with 3 sample products with random price and vat data. You can add as many as you want in the

    /src/main/resources/data.sql