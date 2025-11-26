# 📌 Instructions Capture Service

## ✅ Overview
This service:
- Accepts trade instructions via **file upload (CSV/JSON)**.
- Normalizes data into a canonical format.
- Transforms it into platform-specific JSON.
- Publishes messages to **Kafka**.

---

## ✅ Tech Stack
- Java 17, Spring Boot
- Apache Kafka
- Swagger/OpenAPI
- Docker

---

## 🚀 How to Run Locally
```bash
git clone https://github.com/DariaRska/instructions-capture-service.git
cd instructions-capture-service
mvn spring-boot:run
```

Access Swagger:
```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Endpoints
### Upload Trade File
```
POST /api/trades/upload
```
- Content-Type: `multipart/form-data`
- Parameter: `file` (CSV or JSON)

Example:
```bash
curl -X POST "http://localhost:8080/api/trades/upload" -F "file=@trades.csv"
```

---

## ✅ Kafka Testing
Produce:
```bash
docker exec -it kafka-docker-kafka-1 bash
cd /bin
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic instructions.inbound
```

Consume:
```bash
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic instructions.outbound --from-beginning
```

Expected output:
```json
{"platform_id":"ABC123","account":"****6789","security":"ABC123","type":"BUY","date":"2025-11-26","amount":1000.5}
```

---

## ✅ Run in Docker
Build:
```bash
mvn clean package
docker build -t instructions-service .
```

Run:
```bash
docker run -p 9090:8080 instructions-service
```

Access:
```
http://localhost:9090/swagger-ui.html
```

---

## ✅ Tests
```bash
mvn test
```

---

## ✅ Project Structure
```
src/
├── main/java/com/example/instructions
│   ├── controller/TradeController.java
│   ├── service/TradeService.java
│   ├── service/KafkaPublisher.java
│   ├── util/TradeTransformer.java
│   └── model/CanonicalTrade.java, PlatformTrade.java
├── test/java/com/example/instructions
│   ├── util/TradeTransformerTest.java
│   ├── service/TradeServiceTest.java
│   └── controller/TradeControllerTest.java
```

---

## ✅ Known Issues & Fixes
- **Docker image pull fails for `openjdk:17-jdk-slim`**  
  ✅ Use `eclipse-temurin:17-jdk-alpine` instead in Dockerfile.
- **Port 8080 already in use**  
  ✅ Stop local app or use `docker run -p 9090:8080 instructions-service`.
- **MultipartException during file upload**  
  ✅ Ensure request uses `multipart/form-data` in Postman or curl with `-F`.
- **Kafka not consuming messages**  
  ✅ Check topics exist and Spring Boot logs show listener activity.

---
