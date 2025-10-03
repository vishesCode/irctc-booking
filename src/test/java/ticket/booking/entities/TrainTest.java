package ticket.booking.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Test class for Train entity
 * Tests Train object creation and methods
 */
public class TrainTest {

    private Train train;
    private List<List<Integer>> seats;
    private Map<String, String> stationTimes;
    private List<String> stations;

    @BeforeEach
    public void setUp() {
        // This method runs before each test
        // Setting up common test data
        seats = new ArrayList<>();
        seats.add(Arrays.asList(0, 0, 1, 0));
        seats.add(Arrays.asList(1, 0, 0, 0));
        
        stationTimes = new HashMap<>();
        stationTimes.put("bangalore", "08:00:00");
        stationTimes.put("mumbai", "14:30:00");
        stationTimes.put("delhi", "20:00:00");
        
        stations = Arrays.asList("bangalore", "mumbai", "delhi");
        
        train = new Train("T001", "12345", seats, stationTimes, stations);
    }

    @Test
    @DisplayName("Test Train constructor initializes all fields correctly")
    public void testTrainConstructor() {
        // Assert
        assertEquals("T001", train.getTrainId(), "Train ID should match");
        assertEquals("12345", train.getTrainNo(), "Train number should match");
        assertEquals(2, train.getSeats().size(), "Should have 2 rows of seats");
        assertEquals(3, train.getStations().size(), "Should have 3 stations");
        assertEquals(3, train.getStationTimes().size(), "Should have 3 station times");
    }

    @Test
    @DisplayName("Test default constructor creates empty Train")
    public void testDefaultConstructor() {
        // Act
        Train emptyTrain = new Train();
        
        // Assert
        assertNotNull(emptyTrain, "Default constructor should create a Train object");
    }

    @Test
    @DisplayName("Test Train getTrainInfo returns formatted string")
    public void testGetTrainInfo() {
        // Act
        String trainInfo = train.getTrainInfo();
        
        // Assert
        assertNotNull(trainInfo, "Train info should not be null");
        assertTrue(trainInfo.contains("T001"), "Train info should contain train ID");
        assertTrue(trainInfo.contains("12345"), "Train info should contain train number");
    }

    @Test
    @DisplayName("Test seat modification")
    public void testSeatModification() {
        // Arrange
        List<List<Integer>> newSeats = new ArrayList<>();
        newSeats.add(Arrays.asList(1, 1, 1, 1));
        
        // Act
        train.setSeats(newSeats);
        
        // Assert
        assertEquals(1, train.getSeats().size(), "Should have 1 row of seats after modification");
        assertEquals(4, train.getSeats().get(0).size(), "First row should have 4 seats");
    }

    @Test
    @DisplayName("Test booking a seat changes seat status")
    public void testSeatBooking() {
        // Arrange
        List<List<Integer>> currentSeats = train.getSeats();
        
        // Act: Book seat at row 0, column 0 (change 0 to 1)
        currentSeats.get(0).set(0, 1);
        train.setSeats(currentSeats);
        
        // Assert
        assertEquals(1, train.getSeats().get(0).get(0), 
            "Seat should be marked as booked (1)");
    }

    @Test
    @DisplayName("Test available seat count")
    public void testAvailableSeatCount() {
        // Act: Count available seats (0 values)
        int availableSeats = 0;
        for (List<Integer> row : train.getSeats()) {
            for (Integer seat : row) {
                if (seat == 0) {
                    availableSeats++;
                }
            }
        }
        
        // Assert
        assertEquals(6, availableSeats, 
            "Should have 6 available seats (0 values in the matrix)");
    }

    @Test
    @DisplayName("Test station times retrieval")
    public void testStationTimes() {
        // Act
        Map<String, String> times = train.getStationTimes();
        
        // Assert
        assertEquals("08:00:00", times.get("bangalore"), 
            "Bangalore time should be 08:00:00");
        assertEquals("14:30:00", times.get("mumbai"), 
            "Mumbai time should be 14:30:00");
        assertEquals("20:00:00", times.get("delhi"), 
            "Delhi time should be 20:00:00");
    }

    @Test
    @DisplayName("Test stations list")
    public void testStationsList() {
        // Act
        List<String> trainStations = train.getStations();
        
        // Assert
        assertEquals(3, trainStations.size(), "Should have 3 stations");
        assertTrue(trainStations.contains("bangalore"), "Should include bangalore");
        assertTrue(trainStations.contains("mumbai"), "Should include mumbai");
        assertTrue(trainStations.contains("delhi"), "Should include delhi");
    }

    @Test
    @DisplayName("Test train ID setter")
    public void testSetTrainId() {
        // Act
        train.setTrainId("T999");
        
        // Assert
        assertEquals("T999", train.getTrainId(), "Train ID should be updated");
    }

    @Test
    @DisplayName("Test train number setter")
    public void testSetTrainNo() {
        // Act
        train.setTrainNo("99999");
        
        // Assert
        assertEquals("99999", train.getTrainNo(), "Train number should be updated");
    }

    @Test
    @DisplayName("Test adding new station")
    public void testAddingStation() {
        // Arrange
        List<String> updatedStations = new ArrayList<>(train.getStations());
        updatedStations.add("pune");
        
        Map<String, String> updatedTimes = new HashMap<>(train.getStationTimes());
        updatedTimes.put("pune", "11:00:00");
        
        // Act
        train.setStations(updatedStations);
        train.setStationTimes(updatedTimes);
        
        // Assert
        assertEquals(4, train.getStations().size(), "Should have 4 stations");
        assertTrue(train.getStations().contains("pune"), "Should include new station");
        assertEquals("11:00:00", train.getStationTimes().get("pune"), 
            "New station should have correct time");
    }
}

