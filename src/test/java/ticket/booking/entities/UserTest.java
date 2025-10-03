package ticket.booking.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Test class for User entity
 * Tests User object creation and methods
 */
public class UserTest {

    private User user;
    private List<Ticket> tickets;

    @BeforeEach
    public void setUp() {
        // Set up test data before each test
        tickets = new ArrayList<>();
        user = new User(
            "john_doe",
            "plainPassword",
            "$2a$10$hashedPasswordExample",
            tickets,
            "USER123"
        );
    }

    @Test
    @DisplayName("Test User constructor initializes all fields")
    public void testUserConstructor() {
        // Assert
        assertEquals("john_doe", user.getName(), "Username should match");
        assertEquals("plainPassword", user.getPassword(), "Password should match");
        assertEquals("$2a$10$hashedPasswordExample", user.getHashedPassword(), 
            "Hashed password should match");
        assertEquals("USER123", user.getUserId(), "User ID should match");
        assertNotNull(user.getTicketsBooked(), "Tickets list should not be null");
        assertEquals(0, user.getTicketsBooked().size(), 
            "New user should have no tickets");
    }

    @Test
    @DisplayName("Test default constructor creates empty User")
    public void testDefaultConstructor() {
        // Act
        User emptyUser = new User();
        
        // Assert
        assertNotNull(emptyUser, "Default constructor should create User object");
    }

    @Test
    @DisplayName("Test adding ticket to user")
    public void testAddingTicket() {
        // Arrange
        Ticket ticket = new Ticket(
            "TICKET001",
            "USER123",
            "bangalore",
            "delhi",
            "2025-10-15",
            null
        );
        
        // Act
        user.getTicketsBooked().add(ticket);
        
        // Assert
        assertEquals(1, user.getTicketsBooked().size(), 
            "User should have 1 ticket");
        assertEquals("TICKET001", user.getTicketsBooked().get(0).getTicketId(), 
            "Ticket ID should match");
    }

    @Test
    @DisplayName("Test user with multiple tickets")
    public void testMultipleTickets() {
        // Arrange
        Ticket ticket1 = new Ticket("T1", "USER123", "A", "B", "2025-10-15", null);
        Ticket ticket2 = new Ticket("T2", "USER123", "C", "D", "2025-10-16", null);
        Ticket ticket3 = new Ticket("T3", "USER123", "E", "F", "2025-10-17", null);
        
        // Act
        user.getTicketsBooked().add(ticket1);
        user.getTicketsBooked().add(ticket2);
        user.getTicketsBooked().add(ticket3);
        
        // Assert
        assertEquals(3, user.getTicketsBooked().size(), 
            "User should have 3 tickets");
    }

    @Test
    @DisplayName("Test setters modify User properties")
    public void testSetters() {
        // Act
        user.setName("jane_doe");
        user.setHashedPassword("$2a$10$newHashedPassword");
        user.setUserId("USER999");
        
        // Assert
        assertEquals("jane_doe", user.getName(), "Username should be updated");
        assertEquals("$2a$10$newHashedPassword", user.getHashedPassword(), 
            "Hashed password should be updated");
        assertEquals("USER999", user.getUserId(), "User ID should be updated");
    }

    @Test
    @DisplayName("Test replacing tickets list")
    public void testReplacingTicketsList() {
        // Arrange
        List<Ticket> newTickets = new ArrayList<>();
        newTickets.add(new Ticket("T10", "USER123", "X", "Y", "2025-11-01", null));
        
        // Act
        user.setTicketsBooked(newTickets);
        
        // Assert
        assertEquals(1, user.getTicketsBooked().size(), 
            "User should have 1 ticket in new list");
        assertEquals("T10", user.getTicketsBooked().get(0).getTicketId(), 
            "Ticket ID should match");
    }

    @Test
    @DisplayName("Test removing ticket from user")
    public void testRemovingTicket() {
        // Arrange
        Ticket ticket1 = new Ticket("T1", "USER123", "A", "B", "2025-10-15", null);
        Ticket ticket2 = new Ticket("T2", "USER123", "C", "D", "2025-10-16", null);
        user.getTicketsBooked().add(ticket1);
        user.getTicketsBooked().add(ticket2);
        
        // Act
        user.getTicketsBooked().remove(0);
        
        // Assert
        assertEquals(1, user.getTicketsBooked().size(), 
            "User should have 1 ticket after removal");
        assertEquals("T2", user.getTicketsBooked().get(0).getTicketId(), 
            "Remaining ticket should be T2");
    }

    @Test
    @DisplayName("Test user with null password")
    public void testUserWithNullPassword() {
        // Act
        User userWithNullPassword = new User(
            "test_user",
            null,
            "$2a$10$hashedPassword",
            new ArrayList<>(),
            "USER456"
        );
        
        // Assert
        assertNull(userWithNullPassword.getPassword(), 
            "Password can be null");
        assertNotNull(userWithNullPassword.getHashedPassword(), 
            "Hashed password should not be null");
    }

    @Test
    @DisplayName("Test finding ticket by ID")
    public void testFindingTicketById() {
        // Arrange
        Ticket ticket1 = new Ticket("T1", "USER123", "A", "B", "2025-10-15", null);
        Ticket ticket2 = new Ticket("T2", "USER123", "C", "D", "2025-10-16", null);
        Ticket ticket3 = new Ticket("T3", "USER123", "E", "F", "2025-10-17", null);
        user.getTicketsBooked().add(ticket1);
        user.getTicketsBooked().add(ticket2);
        user.getTicketsBooked().add(ticket3);
        
        // Act
        Optional<Ticket> foundTicket = user.getTicketsBooked().stream()
            .filter(t -> t.getTicketId().equals("T2"))
            .findFirst();
        
        // Assert
        assertTrue(foundTicket.isPresent(), "Ticket T2 should be found");
        assertEquals("T2", foundTicket.get().getTicketId(), 
            "Found ticket should have ID T2");
    }

    @Test
    @DisplayName("Test user ID uniqueness concept")
    public void testUserIdUniqueness() {
        // Arrange
        User user1 = new User("john", "pass", "hash", new ArrayList<>(), "USER001");
        User user2 = new User("jane", "pass", "hash", new ArrayList<>(), "USER002");
        
        // Assert
        assertNotEquals(user1.getUserId(), user2.getUserId(), 
            "Different users should have different IDs");
    }
}

