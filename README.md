# 🏦 Event Booking Microservices Backend

A **scalable, secure, and modular banking backend** built with **Spring Boot**, **MySQL**, **Kafka**, and **OpenFeign** using a **microservice architecture**.  
All APIs are exposed via **API Gateway** on port `4000`.

---

## 🧩 Microservices Overview

| Service | Gateway Route | Description |
|----------|----------------|-------------|
| **Auth Service** | `/auth/**` | Handles user registration, login, and JWT authentication |
| **Event Service** | `/events/**` | Manages Events and seats |
| **Booking Service** | `/booking/**` | Handles Booking of Event Seats |

---

## 🔐 Auth Service (`/auth`)

| HTTP Method | Endpoint | Request | Response |
|-------------|----------|---------|----------|
| POST | `/auth/register` | ```json { "userName": "fahim", "email": "fahim@gmail.com", "password": "fahim1234", "roles": "USER" } ``` | ```json { "id": 1,"userName": "fahim","email": "fahim@gmail.com", "roles": "USER" } ``` |
| POST | `/auth/login` | ```json { "email": "fahim@gmail.com", "password": "fahim1234" } ``` | ```json { "jwtToken": "jwt-token-here", "expiryDate": "12/2/1025T03:28",} ``` |

---

## 👤 Event Service (`/event`)

| HTTP Method | Endpoint | Request | Response |
|-------------|----------|---------|----------|
| POST | `/event` | ```json { "name": "jazz night", "location": "Dhaka, Bangladesh", "startTime": "2025-12-02T20:00:00", "endTime": "2025-12-02T21:30:00", "price": 1000.00 } ``` | ```json { "id": 1, "organizerId": 1, "name": "jazz night" } ``` |
| GET | `/event/{eventId}` | - | ```json { "name": "jazz night", "location": "Dhaka, Bangladesh", "startTime": "2025-12-02T20:00:00", "endTime": "2025-12-02T21:30:00", "price": 1000.00,  "availableSeats": 150, "status": INCOMING} ``` |
| GET | `/event?city="dhaka"&date="2025-12-02&page=1&size=10` | - | ```json [{ "name": "jazz night", "location": "Dhaka, Bangladesh", "startTime": "2025-12-02T20:00:00", "endTime": "2025-12-02T21:30:00", "price": 1000.00,  "availableSeats": 150, "status": INCOMING} ] ``` |

---

## 🏦 Event Service (`/seat`)

| HTTP Method | Endpoint | Request | Response |
|-------------|----------|---------|----------|
| POST | `/seat/avilable-seats` | ```json { "eventId": 1, "seats": [A10] } ``` | ```json [{ "id": 10, eventId: 1, "seatNumber": "A10" , "isBooked": False, "price":1000.00 }] ``` |

---

## 💸 Booking Service (`/booking`)

| HTTP Method | Endpoint | Request | Response |
|-------------|----------|---------|----------|
| POST | `/booking/book-seat` | ```json { "eventId": 1, "seats": ["A1","A2"]} ``` | ```json { "bookingId": 1, "status": "SUCCESS", "message": "Booking ticket A1 and A2 is sucessful", totalPrice: 2000.00 } ``` |

---

