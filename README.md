# 🚀 NotifyHub Lite

A production-grade notification system built with **Java 21 + Spring Boot**, optimized for **low-resource environments (2GB RAM EC2)**.

---

## ✨ Features

* ✅ Async notification processing (non-blocking API)
* 🔁 Retry mechanism with scheduler
* ⚡ In-memory rate limiting (no Redis)
* 🧠 Transaction management
* 📊 Structured logging + Correlation ID
* 🐳 Dockerized with multi-stage build
* 🖥 Simple UI with auto-refresh

---

## 🏗 Architecture

Controller → Service → Repository
Strategy Pattern for Notification Senders (Email/SMS/Webhook)

---

## ⚙️ Tech Stack

* Java 21
* Spring Boot
* PostgreSQL
* Docker & Docker Compose

---

## 🚀 Run Locally

```bash
docker-compose up -d --build
```

Open:
http://localhost:8080

---

## 📸 Demo Flow

1. Create notification
2. Async processing starts
3. Status updates (PENDING → SENT / FAILED → RETRY)

---

## 🧠 Key Concepts

* Async processing without Kafka
* Scheduler-based retry
* Rate limiting without Redis
* Transaction safety in async systems

---

## 🚀 Future Improvements

* Redis-based rate limiter
* Kafka integration
* Flyway migrations
* WebSocket for real-time UI

---
