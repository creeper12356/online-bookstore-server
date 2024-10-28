# BookStore Backend
## How to run this project ? 
### Prepare
Install [Apache Kafka](https://kafka.apache.org/) and configure KAFKA_DIR to .env file

Then run: 
```bash
source .env
make prepare
```

### Run 
```bash
./mvnw spring-boot:run 
```

### Cleanup
```bash
source .env
make cleanup
```