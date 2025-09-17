package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserBookingService {
    private User user;
    private List<User> usersList;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";



    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    private void loadUsers() throws IOException {
        File users = new File(USERS_PATH);
        usersList = objectMapper.readValue(users, new TypeReference<List<User>>() {
        });
    }

    public boolean login() {
        Optional<User> foundUser = usersList.stream()
                .filter(
                        user1 -> {
                            return user1.getName().equals(user.getName()) &&  UserServiceUtil.checkPassword(user1.getHashedPassword(), user.getPassword());
                        }
                ).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1) {
        try {
            usersList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }


    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, usersList);
    }

    public void fetchBookings() {
        Optional<User> userFetched = usersList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if (userFetched.isPresent()) {
            userFetched.get().printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId) {
        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return Boolean.FALSE;
        }
        String finalTicketId1 = ticketId;  //Because strings are immutable
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));
        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        } else {
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }
    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        }catch(IOException ex){
            return new ArrayList<>();
        }
    }

}

