# IRCTC Train Booking System

A command-line based train ticket booking system inspired by the Indian Railway Catering and Tourism Corporation (IRCTC). This project demonstrates core Java programming concepts including object-oriented design, file I/O operations, and data persistence using JSON.

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [Learning Concepts](#learning-concepts)
- [Future Enhancements](#future-enhancements)

## ✨ Features

The application provides the following functionality:

1. **User Authentication**
   - Sign up with username and password
   - Login with secure password hashing using BCrypt
   - User data persistence in JSON format

2. **Train Search**
   - Search trains by source and destination stations
   - View train schedules and station timings

3. **Seat Booking**
   - View available seats in a visual grid format
   - Book seats by selecting row and column
   - Real-time seat availability tracking

4. **Booking Management**
   - View all your booked tickets
   - Cancel existing bookings

## 🛠 Technologies Used

- **Java 24** - Core programming language
- **Maven** - Dependency management and build tool
- **Jackson Databind** (v2.20.0) - JSON serialization/deserialization
- **BCrypt** (v0.4) - Password hashing for security
- **Lombok** (v1.18.38) - Reduces boilerplate code with annotations

## 📁 Project Structure

```
irctc-booking/
├── src/main/java/ticket/booking/
│   ├── App.java                      # Main application entry point
│   ├── entities/                     # Data models
│   │   ├── User.java                 # User entity with tickets
│   │   ├── Train.java                # Train entity with seats & stations
│   │   └── Ticket.java               # Ticket entity
│   ├── services/                     # Business logic
│   │   ├── UserBookingService.java   # User authentication & booking operations
│   │   └── TrainService.java         # Train search and management
│   ├── util/                         # Utility classes
│   │   └── UserServiceUtil.java      # Password hashing utilities
│   └── localDb/                      # JSON-based data storage
│       ├── users.json                # User data
│       └── trains.json               # Train data
└── pom.xml                           # Maven configuration
```

## ✅ Prerequisites

Before running this project, ensure you have:

- **Java Development Kit (JDK) 24** or higher installed
- **Apache Maven 3.6+** installed
- A terminal/command prompt
- A text editor or IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Check Your Installation

```bash
# Check Java version
java -version

# Check Maven version
mvn -version
```

## 💻 Installation and Setup

1. **Clone or download the project** to your local machine

2. **Navigate to the project directory:**
   ```bash
   cd irctc-booking
   ```

3. **Build the project using Maven:**
   ```bash
   mvn clean compile
   ```
   This command will:
   - Download all required dependencies
   - Compile the Java source files
   - Generate the necessary class files in the `target/` directory

## 🚀 How to Run

Execute the following command in the project root directory:

```bash
mvn exec:java -Dexec.mainClass="ticket.booking.App"
```

Alternatively, if you prefer using the compiled classes directly:

```bash
java -cp target/classes ticket.booking.App
```

## 📖 Usage Guide

Once the application starts, you'll see a menu with 7 options:

### 1. Sign Up
- Choose option `1`
- Enter a unique username
- Enter a password (will be securely hashed and stored)

### 2. Login
- Choose option `2`
- Enter your registered username
- Enter your password

### 3. Fetch Bookings
- Choose option `3`
- View all your booked tickets with details

### 4. Search Trains
- Choose option `4`
- Enter source station (e.g., "bangalore")
- Enter destination station (e.g., "delhi")
- View available trains with their schedules

### 5. Book a Seat
- **First search for trains (option 4)** to select a train
- Then choose option `5`
- View the seat layout (0 = available, 1 = booked)
- Enter row number
- Enter column number
- Confirmation message will appear

### 6. Cancel Booking
- Choose option `6`
- Enter the ticket ID to cancel

### 7. Exit
- Choose option `7` to close the application

### Example Workflow

```
1. Sign up → Create account
2. Login → Authenticate
3. Search Trains → Find "bangalore" to "delhi"
4. Book a Seat → Select row 0, column 0
5. Fetch Bookings → View your ticket
6. Exit
```

## 📚 Learning Concepts

This project demonstrates several important Java programming concepts:

### 1. **Object-Oriented Programming (OOP)**
   - **Encapsulation**: Classes like `User`, `Train`, and `Ticket` encapsulate data and behavior
   - **Abstraction**: Services layer abstracts business logic from the presentation layer

### 2. **Data Persistence**
   - JSON-based file storage for users and trains
   - Jackson library for object serialization/deserialization

### 3. **Security**
   - Password hashing using BCrypt algorithm
   - Never storing plain-text passwords

### 4. **Design Patterns**
   - **Service Layer Pattern**: Business logic separated in service classes
   - **Builder Pattern**: Used in `Train` entity with Lombok's `@Builder`

### 5. **Java Libraries & Frameworks**
   - Maven for dependency management
   - Lombok for reducing boilerplate code
   - Jackson for JSON processing

### 6. **Collections Framework**
   - Lists for storing multiple objects (`List<User>`, `List<Train>`)
   - Maps for station-time relationships (`Map<String, String>`)
   - Streams API for filtering and searching

## 🔮 Future Enhancements

Consider implementing these features to expand your learning:

1. **Database Integration**
   - Replace JSON files with MySQL or PostgreSQL
   - Learn JDBC or JPA/Hibernate

2. **REST API**
   - Convert to a Spring Boot web application
   - Create RESTful endpoints

3. **Enhanced Features**
   - Multi-user concurrent booking with locking mechanism
   - Payment gateway integration
   - Email/SMS notifications
   - Seat pricing based on train class
   - Date-based train scheduling

4. **Testing**
   - Add JUnit tests for services
   - Mock database for testing

5. **UI Improvements**
   - Build a web frontend with HTML/CSS/JavaScript
   - Or create a JavaFX desktop GUI

## 🤝 Contributing

This is a learning project. Feel free to fork, modify, and enhance it for your own educational purposes!

## 📝 Notes

- The seat matrix uses `0` for available seats and `1` for booked seats
- JSON files in `localDb/` serve as the database
- User passwords are hashed using BCrypt before storage
- Train and user data persist across application restarts

---

**Happy Learning! 🎓**

