package ticket.booking.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Test class for Ticket entity
 * Tests Ticket object creation and methods
 */
public class TicketTest {

    private Ticket ticket;
    private Train train;

    @BeforeEach
    public void setUp() {
        // Set up test data before each test
        List<List<Integer>> seats = new ArrayList<>();
        seats.add(Arrays.asList(0, 0, 1, 0));
        
        Map<String, String> stationTimes = new HashMap<>();
        stationTimes.put("bangalore", "08:00:00");
        stationTimes.put("delhi", "20:00:00");
        
        List<String> stations = Arrays.asList("bangalore", "delhi");
        
        train = new Train("T001", "12345", seats, stationTimes, stations);
        
        ticket = new Ticket(
            "TICKET001",
            "USER123",
            "bangalore",
            "delhi",
            "2025-10-15",
            train
        );
    }

    @Test
    @DisplayName("Test Ticket constructor initializes all fields")
    public void testTicketConstructor() {
        // Assert
        assertEquals("TICKET001", ticket.getTicketId(), "Ticket ID should match");
        assertEquals("USER123", ticket.getUserId(), "User ID should match");
        assertEquals("bangalore", ticket.getSource(), "Source should match");
        assertEquals("delhi", ticket.getDestination(), "Destination should match");
        assertEquals("2025-10-15", ticket.getDateOfTravel(), "Date should match");
        assertNotNull(ticket.getTrain(), "Train should not be null");
    }

    @Test
    @DisplayName("Test default constructor creates empty Ticket")
    public void testDefaultConstructor() {
        // Act
        Ticket emptyTicket = new Ticket();
        
        // Assert
        assertNotNull(emptyTicket, "Default constructor should create Ticket object");
    }

    @Test
    @DisplayName("Test getTicketInfo returns formatted string")
    public void testGetTicketInfo() {
        // Act
        String ticketInfo = ticket.getTicketInfo();
        
        // Assert
        assertNotNull(ticketInfo, "Ticket info should not be null");
        assertTrue(ticketInfo.contains("TICKET001"), "Should contain ticket ID");
        assertTrue(ticketInfo.contains("USER123"), "Should contain user ID");
        assertTrue(ticketInfo.contains("bangalore"), "Should contain source");
        assertTrue(ticketInfo.contains("delhi"), "Should contain destination");
        assertTrue(ticketInfo.contains("2025-10-15"), "Should contain date");
    }

    @Test
    @DisplayName("Test setters modify Ticket properties")
    public void testSetters() {
        // Act
        ticket.setTicketId("TICKET999");
        ticket.setUserId("USER999");
        ticket.setSource("mumbai");
        ticket.setDestination("pune");
        ticket.setDateOfTravel("2025-12-25");
        
        // Assert
        assertEquals("TICKET999", ticket.getTicketId(), "Ticket ID should be updated");
        assertEquals("USER999", ticket.getUserId(), "User ID should be updated");
        assertEquals("mumbai", ticket.getSource(), "Source should be updated");
        assertEquals("pune", ticket.getDestination(), "Destination should be updated");
        assertEquals("2025-12-25", ticket.getDateOfTravel(), "Date should be updated");
    }

    @Test
    @DisplayName("Test Train association")
    public void testTrainAssociation() {
        // Assert
        assertEquals("T001", ticket.getTrain().getTrainId(), 
            "Ticket should be associated with correct train");
        assertEquals("12345", ticket.getTrain().getTrainNo(), 
            "Train number should match");
    }

    @Test
    @DisplayName("Test updating train in ticket")
    public void testUpdateTrain() {
        // Arrange
        Train newTrain = new Train();
        newTrain.setTrainId("T999");
        newTrain.setTrainNo("99999");
        
        // Act
        ticket.setTrain(newTrain);
        
        // Assert
        assertEquals("T999", ticket.getTrain().getTrainId(), 
            "Train should be updated");
        assertEquals("99999", ticket.getTrain().getTrainNo(), 
            "Train number should be updated");
    }

    @Test
    @DisplayName("Test ticket with null train")
    public void testTicketWithNullTrain() {
        // Act
        Ticket ticketWithoutTrain = new Ticket(
            "TICKET002",
            "USER456",
            "jaipur",
            "agra",
            "2025-11-20",
            null
        );
        
        // Assert
        assertNull(ticketWithoutTrain.getTrain(), 
            "Ticket can be created with null train");
    }

    @Test
    @DisplayName("Test ticket equality based on ID")
    public void testTicketId() {
        // Arrange
        Ticket anotherTicket = new Ticket(
            "TICKET001",  // Same ID
            "USER456",    // Different user
            "pune",       // Different source
            "surat",      // Different destination
            "2025-12-01", // Different date
            null
        );
        
        // Assert
        assertEquals(ticket.getTicketId(), anotherTicket.getTicketId(), 
            "Both tickets should have same ID");
    }

    @Test
    @DisplayName("Test ticket with empty strings")
    public void testTicketWithEmptyStrings() {
        // Act
        Ticket ticketWithEmptyFields = new Ticket("", "", "", "", "", null);
        
        // Assert
        assertEquals("", ticketWithEmptyFields.getTicketId(), 
            "Empty ticket ID should be allowed");
        assertEquals("", ticketWithEmptyFields.getSource(), 
            "Empty source should be allowed");
    }
}

