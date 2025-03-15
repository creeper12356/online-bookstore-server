# An Online Bookstore Backend
## Basic introduction
This project is an online bookstore backend demo with following features:
- Developed using Spring Boot framework, implementing basic features, such as user management, book management, and order management
- Uses MongoDB to manage book reviews
- Uses Neo4j to manage book relationships and implement book recommendations
- Deployed to cloud using Docker
- Implements asynchronous processing and high throughput with Kafka message queue
- Uses Redis caching to enhance performance

Suitable for backend beginners!

## How to run ? 
We use [Docker](https://www.docker.com/) to develop and deploy this project.

Run all Docker containers:
```sh
docker compose up -d
```
For convenience of development and test, we mount project root directory to container directory `/app/online-bookstore-server`, you can start backend server by: 
```sh
docker exec app bash
cd /app/online-bookstore-server
./mvnw spring-boot:run 
```

After all this, you can run the [Frontend](https://github.com/creeper12356/online-bookstore.git) as well and play around. :) 

## How to stop ?
```sh
docker compose down
```
