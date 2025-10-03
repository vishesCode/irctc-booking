# Testing Guide - IRCTC Booking System

## 📚 Introduction to Testing

**What is Testing?**
Testing is the process of verifying that your code works correctly. Automated tests are small programs that check if your main program behaves as expected.

**Why Write Tests?**
1. **Catch bugs early** - Find problems before users do
2. **Confidence in changes** - Safely modify code knowing tests will catch issues
3. **Documentation** - Tests show how your code should be used
4. **Professional practice** - Industry-standard development practice

---

## 🎯 Test Results Summary

```
Tests run: 39
✅ Passed: 39
❌ Failures: 0
⚠️  Errors: 0
⏭️  Skipped: 0
```

### Test Breakdown by Component

| Component | Tests | Description |
|-----------|-------|-------------|
| **UserServiceUtilTest** | 9 | Tests password hashing and verification |
| **TrainTest** | 11 | Tests Train entity operations |
| **TicketTest** | 9 | Tests Ticket entity functionality |
| **UserTest** | 10 | Tests User entity and ticket management |

---

## 📂 Test File Structure

```
src/test/java/ticket/booking/
├── util/
│   └── UserServiceUtilTest.java    (9 tests)
└── entities/
    ├── TrainTest.java              (11 tests)
    ├── TicketTest.java             (9 tests)
    └── UserTest.java               (10 tests)
```

---

## 🔍 Understanding Test Anatomy

### The AAA Pattern

Every test follows the **Arrange-Act-Assert** pattern:

```java
@Test
public void testExample() {
    // 1. ARRANGE: Set up test data
    String password = "myPassword";
    
    // 2. ACT: Perform the action you're testing
    String hashedPassword = UserServiceUtil.hashPassword(password);
    
    // 3. ASSERT: Verify the result
    assertNotNull(hashedPassword, "Hash should not be null");
}
```

### Key Components of a Test

1. **`@Test` Annotation** - Marks a method as a test
2. **`@DisplayName` Annotation** - Provides a readable test description
3. **`@BeforeEach` Annotation** - Runs setup code before each test
4. **Assertions** - Verify expected outcomes

---

## 📝 Test Examples Explained

### Example 1: Simple Verification Test

```java
@Test
@DisplayName("Test password hash is different from plain password")
public void testHashPassword_IsDifferentFromPlainPassword() {
    // Arrange
    String plainPassword = "password123";
    
    // Act
    String hashedPassword = UserServiceUtil.hashPassword(plainPassword);
    
    // Assert
    assertNotEquals(plainPassword, hashedPassword, 
        "Hashed password should be different from plain password");
}
```

**What it tests:** Ensures that password hashing actually transforms the password rather than returning it unchanged.

**Why it matters:** Security - we should never store plain-text passwords.

---

### Example 2: Testing with Objects

```java
@Test
@DisplayName("Test Train constructor initializes all fields correctly")
public void testTrainConstructor() {
    // Arrange & Act
    Train train = new Train("T001", "12345", seats, stationTimes, stations);
    
    // Assert
    assertEquals("T001", train.getTrainId(), "Train ID should match");
    assertEquals("12345", train.getTrainNo(), "Train number should match");
    assertEquals(2, train.getSeats().size(), "Should have 2 rows of seats");
}
```

**What it tests:** Verifies that the Train constructor properly initializes all fields.

**Why it matters:** Ensures objects are created correctly with all their data.

---

### Example 3: Testing Edge Cases

```java
@Test
@DisplayName("Test empty password can be hashed")
public void testHashPassword_WithEmptyPassword() {
    // Arrange
    String emptyPassword = "";
    
    // Act
    String hashedPassword = UserServiceUtil.hashPassword(emptyPassword);
    
    // Assert
    assertNotNull(hashedPassword, "Empty password should still generate a hash");
    assertFalse(hashedPassword.isEmpty(), "Hash should not be empty");
}
```

**What it tests:** How the system handles unusual or "edge case" inputs.

**Why it matters:** Real-world applications must handle unexpected inputs gracefully.

---

## 🛠 Common Assertion Methods

| Assertion | Purpose | Example |
|-----------|---------|---------|
| `assertEquals(expected, actual)` | Values are equal | `assertEquals(5, list.size())` |
| `assertNotEquals(a, b)` | Values are different | `assertNotEquals(plain, hashed)` |
| `assertTrue(condition)` | Condition is true | `assertTrue(user.isActive())` |
| `assertFalse(condition)` | Condition is false | `assertFalse(list.isEmpty())` |
| `assertNull(object)` | Object is null | `assertNull(user.getTicket())` |
| `assertNotNull(object)` | Object is not null | `assertNotNull(train)` |

---

## 🚀 Running Tests

### Option 1: Run All Tests (Recommended)
```bash
mvn test
```
Runs all test files and shows a summary.

### Option 2: Run a Specific Test Class
```bash
mvn test -Dtest=UserServiceUtilTest
```
Runs only the UserServiceUtilTest class.

### Option 3: Run a Single Test Method
```bash
mvn test -Dtest=UserServiceUtilTest#testHashPassword_ReturnsNonNullHash
```
Runs just one specific test method.

### Option 4: Clean and Test
```bash
mvn clean test
```
Deletes old build files and runs fresh tests.

---

## 📊 Understanding Test Output

### Successful Test Run
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running ticket.booking.util.UserServiceUtilTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
```
✅ All tests passed!

### Failed Test Example
```
[ERROR] Tests run: 9, Failures: 1, Errors: 0, Skipped: 0
[ERROR] testHashPassword_ReturnsNonNullHash
  Expected: not null
  Actual: null
```
❌ A test failed - fix the code and re-run.

---

## 🎓 What Each Test File Tests

### 1. UserServiceUtilTest (9 tests)

**Purpose:** Tests password security functionality

**Key Tests:**
- ✅ Password hashing produces non-null hash
- ✅ Hash is different from plain password
- ✅ Same password generates different hashes (salting)
- ✅ Correct password verification returns true
- ✅ Incorrect password verification returns false
- ✅ Password verification is case-sensitive
- ✅ Empty passwords can be hashed
- ✅ Special characters work correctly
- ✅ Very long passwords are handled

**Why important:** Password security is critical for user safety.

---

### 2. TrainTest (11 tests)

**Purpose:** Tests Train entity and seat management

**Key Tests:**
- ✅ Constructor initializes all fields
- ✅ Default constructor works
- ✅ getTrainInfo returns formatted string
- ✅ Seat modification works
- ✅ Seat booking changes status
- ✅ Available seat count is correct
- ✅ Station times retrieval works
- ✅ Stations list is correct
- ✅ Setters update values
- ✅ Adding new stations works

**Why important:** Train data integrity is core to the booking system.

---

### 3. TicketTest (9 tests)

**Purpose:** Tests Ticket entity functionality

**Key Tests:**
- ✅ Constructor initializes all fields
- ✅ Default constructor works
- ✅ getTicketInfo returns formatted string
- ✅ Setters modify properties
- ✅ Train association works
- ✅ Updating train in ticket works
- ✅ Ticket with null train is allowed
- ✅ Ticket ID uniqueness
- ✅ Empty strings are handled

**Why important:** Tickets are the main output of the booking system.

---

### 4. UserTest (10 tests)

**Purpose:** Tests User entity and ticket management

**Key Tests:**
- ✅ Constructor initializes all fields
- ✅ Default constructor works
- ✅ Adding tickets to user works
- ✅ Multiple tickets can be managed
- ✅ Setters modify properties
- ✅ Replacing tickets list works
- ✅ Removing tickets works
- ✅ Null passwords are handled
- ✅ Finding tickets by ID works
- ✅ User ID uniqueness concept

**Why important:** User management is essential for personalized bookings.

---

## 🌟 Best Practices We Follow

### 1. **Descriptive Test Names**
```java
// ❌ Bad
@Test
public void test1() { }

// ✅ Good
@Test
@DisplayName("Test password verification is case-sensitive")
public void testCheckPassword_IsCaseSensitive() { }
```

### 2. **Test One Thing at a Time**
```java
// ❌ Bad - testing multiple things
@Test
public void testEverything() {
    // test hashing
    // test verification
    // test edge cases
}

// ✅ Good - focused tests
@Test
public void testHashPassword_ReturnsNonNullHash() { }

@Test
public void testCheckPassword_WithCorrectPassword() { }
```

### 3. **Use Setup Methods**
```java
@BeforeEach
public void setUp() {
    // This runs before EACH test
    user = new User("john", "pass", "hash", tickets, "USER123");
}
```

### 4. **Include Helpful Messages**
```java
// ❌ Less helpful
assertEquals(5, list.size());

// ✅ More helpful
assertEquals(5, list.size(), "User should have 5 tickets after additions");
```

---

## 🐛 When Tests Fail

### Step 1: Read the Error Message
```
Expected: true
Actual: false
```
Understand what was expected vs. what actually happened.

### Step 2: Check the Test
- Is the test correct?
- Are you testing the right thing?

### Step 3: Check the Code
- Does the implementation match the expectation?
- Are there any logic errors?

### Step 4: Debug
- Add print statements
- Run the test in debug mode
- Check input and output values

### Step 5: Fix and Re-run
```bash
mvn test
```

---

## 📈 Test Coverage

**What is Test Coverage?**
The percentage of your code that is executed by tests.

**Our Coverage:**
- ✅ UserServiceUtil: 100% (all methods tested)
- ✅ Entity Constructors: 100%
- ✅ Entity Getters/Setters: ~90%
- ⚠️  Service Layer: 0% (not yet tested)

**Next Steps:**
Consider adding tests for:
1. UserBookingService (signup, login, booking)
2. TrainService (search, updates)
3. Integration tests (full booking flow)

---

## 🎯 Practice Exercises

### Exercise 1: Add a New Test
Add a test to check if a user can have a very long name (100+ characters).

```java
@Test
@DisplayName("Test user with very long name")
public void testUserWithLongName() {
    // Arrange
    String longName = "a".repeat(100);
    
    // Act
    User user = new User(longName, "pass", "hash", new ArrayList<>(), "USER1");
    
    // Assert
    assertEquals(100, user.getName().length(), "Name should be 100 characters");
}
```

### Exercise 2: Test a Bug Fix
Imagine you fix a bug where booking seat (-1, -1) crashed the app. Write a test:

```java
@Test
@DisplayName("Test booking invalid seat returns false")
public void testBookInvalidSeat() {
    // Arrange
    Train train = createTestTrain();
    
    // Act
    Boolean result = bookTrainSeat(train, -1, -1);
    
    // Assert
    assertFalse(result, "Booking invalid seat should return false");
}
```

---

## 📚 Further Learning

### Recommended Topics
1. **Mocking** - Creating fake objects for testing
2. **Integration Tests** - Testing multiple components together
3. **Test-Driven Development (TDD)** - Writing tests before code
4. **Code Coverage Tools** - Measuring test coverage
5. **Parameterized Tests** - Running same test with different inputs

### Resources
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Effective Java Testing](https://www.baeldung.com/java-unit-testing-best-practices)
- [Test-Driven Development by Example](https://www.amazon.com/Test-Driven-Development-Kent-Beck/dp/0321146530)

---

## 💡 Key Takeaways

1. **Tests catch bugs early** - Before they reach production
2. **Tests are documentation** - They show how code should work
3. **Tests give confidence** - Change code without fear
4. **Follow AAA pattern** - Arrange, Act, Assert
5. **Test edge cases** - Empty, null, negative, very large values
6. **Good names matter** - Tests should be self-explanatory
7. **Keep tests simple** - One test, one concept

---

## 🚀 Next Steps

1. **Run the tests yourself:**
   ```bash
   mvn test
   ```

2. **Read through the test files** to understand what they do

3. **Modify a test** and see it fail, then fix it

4. **Write a new test** for a feature you add

5. **Try TDD** - Write tests before implementing new features

---

**Remember:** Good tests make good developers great! 🎓

---

**Created:** October 3, 2025  
**Version:** 1.0

