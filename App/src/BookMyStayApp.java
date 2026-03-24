/**
 * UseCase6RoomAllocationService
 *
 * This class demonstrates booking confirmation and room allocation.
 * It ensures FIFO processing, unique room assignment, and immediate
 * inventory updates to prevent double-booking.
 *
 * @author YourName
 * @version 6.0
 */

import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v6.0 ");
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        // Initialize booking queue
        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Alice", "Single Room"));
        queue.offer(new Reservation("Bob", "Single Room"));
        queue.offer(new Reservation("Charlie", "Single Room")); // exceeds availability
        queue.offer(new Reservation("Diana", "Double Room"));

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Process all requests
        bookingService.processBookings(queue);

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

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// -------------------- BOOKING SERVICE --------------------
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs per type
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Process booking requests in FIFO order
     */
    public void processBookings(Queue<Reservation> queue) {

        System.out.println("\n--- Processing Booking Requests ---\n");

        while (!queue.isEmpty()) {

            Reservation request = queue.poll();
            String roomType = request.getRoomType();

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness using Set
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());
                Set<String> roomSet = allocatedRooms.get(roomType);

                if (!roomSet.contains(roomId)) {

                    // Allocate room
                    roomSet.add(roomId);

                    // Update inventory immediately
                    inventory.decrement(roomType);

                    // Confirm booking
                    System.out.println("Booking Confirmed: " +
                            request.getGuestName() +
                            " | Room Type: " + roomType +
                            " | Room ID: " + roomId);

                }

            } else {
                System.out.println("Booking Failed (No Availability): " +
                        request.getGuestName() +
                        " | Room Type: " + roomType);
            }
        }
    }

    /**
     * Generate unique room ID
     */
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}