/**
 * UseCase10BookingCancellation
 *
 * This class demonstrates safe booking cancellation with rollback.
 * It ensures inventory restoration and prevents invalid cancellations.
 *
 * Uses Stack (LIFO) to track released room IDs.
 *
 * @author YourName
 * @version 10.0
 */

import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v10.0 ");
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 1);

        // Simulated confirmed bookings
        Map<String, String> activeBookings = new HashMap<>();
        activeBookings.put("RES-1001", "Single Room");

        // Track allocated room IDs
        Map<String, Set<String>> allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single Room", new HashSet<>(Arrays.asList("SR-101")));

        // Stack for rollback tracking
        Stack<String> rollbackStack = new Stack<>();

        // Cancellation service
        CancellationService cancellationService =
                new CancellationService(inventory, activeBookings, allocatedRooms, rollbackStack);

        // Perform cancellation
        cancellationService.cancelBooking("RES-1001");

        // Attempt invalid cancellation
        cancellationService.cancelBooking("RES-9999");

        System.out.println("\nApplication terminating...");
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void increment(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// -------------------- CANCELLATION SERVICE --------------------
class CancellationService {

    private RoomInventory inventory;
    private Map<String, String> activeBookings; // reservationId -> roomType
    private Map<String, Set<String>> allocatedRooms; // roomType -> roomIds
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory,
                               Map<String, String> activeBookings,
                               Map<String, Set<String>> allocatedRooms,
                               Stack<String> rollbackStack) {
        this.inventory = inventory;
        this.activeBookings = activeBookings;
        this.allocatedRooms = allocatedRooms;
        this.rollbackStack = rollbackStack;
    }

    /**
     * Cancel a booking safely
     */
    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        // Validate booking existence
        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid reservation ID");
            return;
        }

        String roomType = activeBookings.get(reservationId);

        // Get allocated room IDs
        Set<String> roomSet = allocatedRooms.get(roomType);

        if (roomSet == null || roomSet.isEmpty()) {
            System.out.println("Cancellation Failed: No allocated rooms found");
            return;
        }

        // LIFO rollback: remove last allocated room
        String roomId = roomSet.iterator().next(); // simulate latest allocation
        roomSet.remove(roomId);

        // Push to rollback stack
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increment(roomType);

        // Remove booking
        activeBookings.remove(reservationId);

        System.out.println("Cancellation Successful: " + reservationId +
                " | Released Room ID: " + roomId +
                " | Updated Availability: " + inventory.getAvailability(roomType));
    }
}