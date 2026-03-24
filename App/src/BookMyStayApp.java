/**
 * UseCase9ErrorHandlingValidation
 *
 * This class demonstrates validation and error handling in the booking system.
 * It ensures invalid inputs are caught early using custom exceptions and
 * prevents inconsistent system state.
 *
 * @author YourName
 * @version 9.0
 */

import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v9.0 ");
        System.out.println("=======================================");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 1);

        BookingService bookingService = new BookingService(inventory);

        // Test cases (valid + invalid)
        List<Reservation> requests = Arrays.asList(
                new Reservation("Alice", "Single Room"),   // valid
                new Reservation("Bob", "Double Room"),     // invalid room type
                new Reservation("Charlie", "Single Room"), // no availability
                new Reservation("", "Single Room")         // invalid name
        );

        for (Reservation r : requests) {
            try {
                bookingService.processBooking(r);
            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\nApplication terminating...");
    }
}

// -------------------- RESERVATION --------------------
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- CUSTOM EXCEPTION --------------------
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void decrement(String type) throws InvalidBookingException {

        int current = inventory.getOrDefault(type, -1);

        if (current <= 0) {
            throw new InvalidBookingException("No available rooms for: " + type);
        }

        inventory.put(type, current - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

// -------------------- BOOKING SERVICE --------------------
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Process a single booking with validation
     */
    public void processBooking(Reservation request) throws InvalidBookingException {

        // Validate guest name
        if (request.getGuestName() == null || request.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Validate room type
        if (!inventory.isValidRoomType(request.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + request.getRoomType());
        }

        // Validate availability (fail-fast)
        if (inventory.getAvailability(request.getRoomType()) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + request.getRoomType());
        }

        // If all validations pass → allocate room
        inventory.decrement(request.getRoomType());

        System.out.println("Booking Confirmed: " +
                request.getGuestName() +
                " | Room Type: " + request.getRoomType());
    }
}