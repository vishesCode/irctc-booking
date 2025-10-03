# High-Level Design (HLD) - IRCTC Train Booking System

## 1. System Overview

### 1.1 Purpose
The IRCTC Train Booking System is a command-line application that simulates a railway ticket reservation system. It enables users to search for trains, book seats, manage bookings, and handle user authentication with secure password storage.

### 1.2 Scope
- User registration and authentication
- Train search based on source and destination
- Seat availability visualization and booking
- Booking management (view and cancel)
- Data persistence using JSON files

### 1.3 System Type
**Monolithic Console Application** with file-based data storage

---

## 2. System Architecture

### 2.1 Architectural Pattern

The system follows a **Layered Architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────────────────┐
│            Presentation Layer (CLI)                  │
│              App.java (Main)                         │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│              Service Layer                           │
│  ┌──────────────────┐    ┌──────────────────┐      │
│  │UserBookingService│    │  TrainService     │      │
│  └──────────────────┘    └──────────────────┘      │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│              Utility Layer                           │
│            UserServiceUtil (BCrypt)                  │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│              Data Layer (Entities)                   │
│    User, Train, Ticket                              │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│         Persistence Layer (JSON Files)               │
│      users.json       trains.json                    │
└─────────────────────────────────────────────────────┘
```

### 2.2 Layer Responsibilities

| Layer | Responsibility | Components |
|-------|---------------|------------|
| **Presentation** | User interaction, input/output handling | App.java |
| **Service** | Business logic, orchestration | UserBookingService, TrainService |
| **Utility** | Reusable helper functions | UserServiceUtil |
| **Entity** | Data models and domain objects | User, Train, Ticket |
| **Persistence** | Data storage and retrieval | JSON files + ObjectMapper |

---

## 3. Component Design

### 3.1 Core Components

#### 3.1.1 App (Main Entry Point)
```
┌─────────────────────────────────────┐
│            App.java                 │
├─────────────────────────────────────┤
│ Responsibilities:                   │
│ • Display menu options              │
│ • Accept user input                 │
│ • Route requests to services        │
│ • Handle user session flow          │
├─────────────────────────────────────┤
│ Dependencies:                       │
│ • Scanner (user input)              │
│ • UserBookingService                │
└─────────────────────────────────────┘
```

#### 3.1.2 UserBookingService
```
┌──────────────────────────────────────┐
│      UserBookingService.java         │
├──────────────────────────────────────┤
│ Responsibilities:                    │
│ • User authentication (login/signup)│
│ • Booking operations                 │
│ • User data management               │
│ • Delegate train operations          │
├──────────────────────────────────────┤
│ Key Methods:                         │
│ + signUp(User): Boolean              │
│ + login(): boolean                   │
│ + fetchBookings(): void              │
│ + getTrains(src, dest): List<Train>  │
│ + bookTrainSeat(...): Boolean        │
│ + cancelBooking(ticketId): Boolean   │
├──────────────────────────────────────┤
│ Dependencies:                        │
│ • TrainService                       │
│ • ObjectMapper (Jackson)             │
│ • UserServiceUtil                    │
└──────────────────────────────────────┘
```

#### 3.1.3 TrainService
```
┌──────────────────────────────────────┐
│        TrainService.java             │
├──────────────────────────────────────┤
│ Responsibilities:                    │
│ • Load train data from JSON          │
│ • Search trains by route             │
│ • Update train seat availability     │
│ • Persist train data                 │
├──────────────────────────────────────┤
│ Key Methods:                         │
│ + searchTrains(src, dest): List      │
│ + addTrain(Train): void              │
│ + loadTrains(): void                 │
├──────────────────────────────────────┤
│ Dependencies:                        │
│ • ObjectMapper (Jackson)             │
│ • trains.json file                   │
└──────────────────────────────────────┘
```

#### 3.1.4 UserServiceUtil
```
┌──────────────────────────────────────┐
│      UserServiceUtil.java            │
├──────────────────────────────────────┤
│ Responsibilities:                    │
│ • Password hashing                   │
│ • Password verification              │
├──────────────────────────────────────┤
│ Key Methods:                         │
│ + hashPassword(String): String       │
│ + checkPassword(hash, pwd): boolean  │
├──────────────────────────────────────┤
│ Dependencies:                        │
│ • BCrypt library                     │
└──────────────────────────────────────┘
```

---

## 4. Data Models

### 4.1 Entity Relationship

```
┌─────────────────────────┐
│         User            │
├─────────────────────────┤
│ - userId: String (PK)   │
│ - name: String          │
│ - password: String      │
│ - hashedPassword: String│
│ - ticketsBooked: List   │
└───────────┬─────────────┘
            │ 1:N
            │ has
            ▼
┌─────────────────────────┐
│        Ticket           │
├─────────────────────────┤
│ - ticketId: String (PK) │
│ - trainId: String (FK)  │
│ - seatNumber: String    │
│ - userId: String (FK)   │
│ - source: String        │
│ - destination: String   │
└───────────┬─────────────┘
            │ N:1
            │ for
            ▼
┌─────────────────────────┐
│         Train           │
├─────────────────────────┤
│ - trainId: String (PK)  │
│ - trainNo: String       │
│ - seats: List<List<Int>>│
│ - stations: List<String>│
│ - stationTimes: Map     │
└─────────────────────────┘
```

### 4.2 Data Structures

#### User Entity
```json
{
  "user_id": "uuid-string",
  "name": "username",
  "password": "plain-text (not stored)",
  "hashed_password": "bcrypt-hash",
  "tickets_booked": [
    { ticket objects }
  ]
}
```

#### Train Entity
```json
{
  "train_id": "unique-id",
  "train_no": "12345",
  "seats": [
    [0,0,1,0,0,0],
    [1,0,0,0,1,0]
  ],
  "stations": ["bangalore", "jaipur", "delhi"],
  "station_times": {
    "bangalore": "13:50:00",
    "jaipur": "18:30:00",
    "delhi": "22:15:00"
  }
}
```

**Seat Matrix Convention:**
- `0` = Available seat
- `1` = Booked seat

---

## 5. Key Workflows

### 5.1 User Registration Flow

```
┌──────┐      ┌──────┐      ┌──────────────┐      ┌──────────┐
│ User │      │ App  │      │UserBooking   │      │users.json│
│      │      │      │      │  Service     │      │          │
└───┬──┘      └───┬──┘      └──────┬───────┘      └────┬─────┘
    │             │                 │                   │
    │ 1. Sign Up  │                 │                   │
    │────────────>│                 │                   │
    │             │                 │                   │
    │ 2. Enter    │                 │                   │
    │  credentials│                 │                   │
    │────────────>│                 │                   │
    │             │                 │                   │
    │             │ 3. hashPassword()│                  │
    │             │────────────────>│                   │
    │             │                 │                   │
    │             │ 4. signUp(user) │                   │
    │             │────────────────>│                   │
    │             │                 │                   │
    │             │                 │ 5. Write to file  │
    │             │                 │──────────────────>│
    │             │                 │                   │
    │             │ 6. Success      │                   │
    │             │<────────────────│                   │
    │             │                 │                   │
    │ 7. Confirmation                │                   │
    │<────────────│                 │                   │
    │             │                 │                   │
```

### 5.2 Train Search and Booking Flow

```
┌──────┐    ┌──────┐    ┌────────────┐    ┌──────────┐    ┌──────────┐
│ User │    │ App  │    │UserBooking │    │  Train   │    │trains    │
│      │    │      │    │  Service   │    │ Service  │    │  .json   │
└───┬──┘    └───┬──┘    └─────┬──────┘    └────┬─────┘    └────┬─────┘
    │           │              │                │               │
    │ 1. Search │              │                │               │
    │  Trains   │              │                │               │
    │──────────>│              │                │               │
    │           │              │                │               │
    │           │ 2. getTrains(src, dest)       │               │
    │           │─────────────>│                │               │
    │           │              │                │               │
    │           │              │ 3. searchTrains()              │
    │           │              │───────────────>│               │
    │           │              │                │               │
    │           │              │                │ 4. Load trains│
    │           │              │                │──────────────>│
    │           │              │                │               │
    │           │              │ 5. Filtered trains             │
    │           │              │<───────────────│               │
    │           │              │                │               │
    │           │ 6. Train list│                │               │
    │           │<─────────────│                │               │
    │           │              │                │               │
    │ 7. Display│              │                │               │
    │<──────────│              │                │               │
    │           │              │                │               │
    │ 8. Select │              │                │               │
    │   train & │              │                │               │
    │   seat    │              │                │               │
    │──────────>│              │                │               │
    │           │              │                │               │
    │           │ 9. bookTrainSeat()            │               │
    │           │─────────────>│                │               │
    │           │              │                │               │
    │           │              │ 10. Update seat matrix         │
    │           │              │───────────────>│               │
    │           │              │                │               │
    │           │              │                │ 11. Save      │
    │           │              │                │──────────────>│
    │           │              │                │               │
    │           │ 12. Success  │                │               │
    │           │<─────────────│                │               │
    │           │              │                │               │
    │ 13. Confirmation         │                │               │
    │<──────────│              │                │               │
```

### 5.3 Authentication Flow

```
User enters credentials
        │
        ▼
Load all users from users.json
        │
        ▼
Stream through users list
        │
        ▼
Filter: Check username match
        │
        ▼
BCrypt.checkpw(input, stored_hash)
        │
        ├─── Match ────> Login successful
        │                Create user session
        │
        └─── No Match ──> Login failed
                         Return to menu
```

---

## 6. Design Decisions

### 6.1 Technology Choices

| Decision | Choice | Rationale |
|----------|--------|-----------|
| **Language** | Java 24 | Strong typing, OOP support, rich ecosystem |
| **Build Tool** | Maven | Industry-standard, dependency management |
| **Data Storage** | JSON Files | Simple, human-readable, no DB setup needed |
| **JSON Library** | Jackson | Fast, widely-used, powerful serialization |
| **Password Security** | BCrypt | Industry-standard, salted hashing |
| **Code Generation** | Lombok | Reduces boilerplate (builders, getters) |

### 6.2 Architectural Decisions

#### 6.2.1 Layered Architecture
**Decision:** Use layered architecture with separation of concerns

**Rationale:**
- Makes code maintainable and testable
- Clear responsibility boundaries
- Easy to modify individual layers without affecting others

#### 6.2.2 File-Based Storage
**Decision:** Use JSON files instead of database

**Rationale:**
- Simplicity for learning purposes
- No external dependencies or setup
- Easy to inspect and debug data
- Sufficient for single-user CLI application

**Trade-offs:**
- Not suitable for concurrent users
- Limited query capabilities
- Full file read/write on each operation

#### 6.2.3 Service Layer Pattern
**Decision:** Create separate service classes for business logic

**Rationale:**
- Separates business logic from presentation
- Reusable services
- Easier to test and maintain

#### 6.2.4 In-Memory Processing
**Decision:** Load data into memory, process, and save back

**Rationale:**
- Simple implementation
- Acceptable for small datasets
- No complex transaction handling needed

**Trade-offs:**
- Data loss risk if app crashes before save
- Not scalable for large datasets

---

## 7. Security Considerations

### 7.1 Password Security

```
Plain Password ──> BCrypt Hash ──> Store in JSON
    (never stored)     (salted)      (persistent)

Verification:
User Input ──> BCrypt.checkpw(input, stored_hash) ──> Boolean
```

**Security Features:**
- Passwords hashed using BCrypt with automatic salting
- Plain passwords never stored
- One-way hashing (cannot reverse engineer)

**Limitations:**
- No password complexity validation
- No rate limiting on login attempts
- No session timeout mechanism

### 7.2 Data Protection

**Current State:**
- JSON files stored in plain text
- No encryption at rest
- No access control on files

**Production Recommendations:**
- Encrypt sensitive data at rest
- Implement proper access control
- Use secure credential storage

---

## 8. Data Flow Diagram

### 8.1 Overall System Data Flow

```
┌───────────────────────────────────────────────────────────┐
│                         User                               │
└────────────────────┬──────────────────────────────────────┘
                     │
                     │ Menu Interactions
                     ▼
┌───────────────────────────────────────────────────────────┐
│                      App (CLI)                             │
│  • Input validation                                        │
│  • Menu display                                            │
│  • Flow control                                            │
└────────────────────┬──────────────────────────────────────┘
                     │
         ┌───────────┴────────────┐
         ▼                        ▼
┌──────────────────┐    ┌──────────────────┐
│ UserBooking      │    │  TrainService    │
│ Service          │───>│                  │
│                  │    │                  │
│ • Auth logic     │    │ • Search logic   │
│ • Booking logic  │    │ • Seat updates   │
└────────┬─────────┘    └────────┬─────────┘
         │                       │
         │                       │
         ▼                       ▼
┌──────────────────┐    ┌──────────────────┐
│ users.json       │    │ trains.json      │
│                  │    │                  │
│ [User objects]   │    │ [Train objects]  │
└──────────────────┘    └──────────────────┘
```

---

## 9. Scalability & Performance

### 9.1 Current Limitations

| Aspect | Limitation | Impact |
|--------|------------|--------|
| **Concurrency** | Single-threaded, file locks | Multiple users cannot book simultaneously |
| **Data Volume** | Full file load into memory | Performance degrades with large datasets |
| **Search** | Linear search through lists | O(n) complexity, slow for many trains |
| **Persistence** | Full file rewrite on each change | Inefficient, risk of data corruption |

### 9.2 Scalability Recommendations

#### For Production System:

1. **Database Migration**
   - Replace JSON with relational database (MySQL/PostgreSQL)
   - Use connection pooling
   - Implement proper indexing

2. **Concurrency Handling**
   - Implement row-level locking for seat booking
   - Use optimistic locking with version numbers
   - Add transaction support

3. **Caching**
   - Cache frequently searched routes
   - Use Redis for session management
   - Implement train data caching

4. **API Layer**
   - Convert to REST API with Spring Boot
   - Support multiple concurrent clients
   - Implement rate limiting

5. **Search Optimization**
   - Index stations and routes
   - Use full-text search for station names
   - Implement pagination for results

---

## 10. Future Enhancements

### 10.1 Functional Enhancements
- Date-based train scheduling
- Multiple train classes (Sleeper, AC, etc.)
- Dynamic pricing
- Waitlist management
- Refund processing

### 10.2 Technical Enhancements
- Database integration (JPA/Hibernate)
- RESTful API with Spring Boot
- Web frontend (React/Angular)
- Unit and integration tests
- Logging framework (Log4j/SLF4J)
- Docker containerization
- CI/CD pipeline

### 10.3 Security Enhancements
- JWT-based authentication
- Role-based access control (Admin/User)
- API rate limiting
- Input sanitization
- Audit logging

---

## 11. Testing Strategy

### 11.1 Recommended Test Coverage

```
┌─────────────────────────────────────────┐
│           Unit Tests                    │
├─────────────────────────────────────────┤
│ • UserServiceUtil (BCrypt functions)    │
│ • Entity validations                    │
│ • Service layer methods                 │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│        Integration Tests                │
├─────────────────────────────────────────┤
│ • File I/O operations                   │
│ • Service interactions                  │
│ • Complete booking flow                 │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│         End-to-End Tests                │
├─────────────────────────────────────────┤
│ • Complete user journeys                │
│ • Sign up → Search → Book → View        │
└─────────────────────────────────────────┘
```

---

## 12. Deployment Architecture

### 12.1 Current Deployment

```
Local Machine
    │
    ├── Java Runtime (JDK 24)
    ├── Maven
    └── Application JAR
            │
            ├── Application Code
            └── Data Files (JSON)
```

### 12.2 Recommended Production Architecture

```
┌─────────────────────────────────────────────┐
│              Load Balancer                   │
└──────────────────┬──────────────────────────┘
                   │
        ┌──────────┴──────────┐
        ▼                     ▼
┌───────────────┐      ┌───────────────┐
│  App Server 1 │      │  App Server 2 │
│  (Spring Boot)│      │  (Spring Boot)│
└───────┬───────┘      └───────┬───────┘
        │                      │
        └──────────┬───────────┘
                   ▼
        ┌─────────────────────┐
        │   Database Cluster  │
        │  (MySQL/PostgreSQL) │
        └─────────────────────┘
                   │
                   ▼
        ┌─────────────────────┐
        │   Cache Layer       │
        │     (Redis)         │
        └─────────────────────┘
```

---

## 13. Glossary

| Term | Definition |
|------|------------|
| **CLI** | Command-Line Interface - text-based user interface |
| **BCrypt** | Password hashing algorithm with built-in salt |
| **Jackson** | Java library for JSON serialization/deserialization |
| **Maven** | Build automation and dependency management tool |
| **Lombok** | Java library that reduces boilerplate code |
| **DTO** | Data Transfer Object - object that carries data |
| **POJO** | Plain Old Java Object - simple Java class |

---

## 14. References

### 14.1 Technology Documentation
- [Java 24 Documentation](https://docs.oracle.com/en/java/)
- [Jackson Documentation](https://github.com/FasterXML/jackson)
- [BCrypt Documentation](https://github.com/jeremyh/jBCrypt)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Lombok Documentation](https://projectlombok.org/)

### 14.2 Design Patterns
- Layered Architecture Pattern
- Service Layer Pattern
- Builder Pattern (Lombok)

---

**Document Version:** 1.0  
**Last Updated:** October 3, 2025  
**Author:** System Architecture Team

