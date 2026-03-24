/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates saving and restoring system state (inventory & bookings)
 * using Java serialization to survive application restarts.
 *
 * Author: YourName
 * Version: 12.0
 */

import java.io.*;
import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v12.0 ");
        System.out.println("=======================================");

        // Initialize persistence service
        PersistenceService persistenceService = new PersistenceService("hotel_state.dat");

        // Try restoring previous state
        HotelState state = persistenceService.loadState();
        if (state == null) {
            // No previous state, create fresh
            state = new HotelState();
            state.inventory.addRoomType("Single Room", 2);
            state.inventory.addRoomType("Double Room", 1);

            state.bookingHistory.add(new Reservation("Alice", "Single Room"));
            state.bookingHistory.add(new Reservation("Bob", "Double Room"));
            System.out.println("No previous state found. Initialized fresh hotel state.");
        } else {
            System.out.println("Previous state successfully restored.");
        }

        // Show current state
        System.out.println("\nCurrent Booking History:");
        for (Reservation r : state.bookingHistory) {
            System.out.println(r.getGuestName() + " -> " + r.getRoomType());
        }

        System.out.println("\nCurrent Inventory:");
        state.inventory.printInventory();

        // Simulate shutdown: save current state
        persistenceService.saveState(state);
        System.out.println("\nState saved successfully. Application terminating...");
    }
}

// -------------------- HOTEL STATE --------------------
class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;

    public RoomInventory inventory;
    public List<Reservation> bookingHistory;

    public HotelState() {
        inventory = new RoomInventory();
        bookingHistory = new ArrayList<>();
    }
}

// -------------------- RESERVATION --------------------
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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

// -------------------- ROOM INVENTORY --------------------
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void printInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

// -------------------- PERSISTENCE SERVICE --------------------
class PersistenceService {

    private String filename;

    public PersistenceService(String filename) {
        this.filename = filename;
    }

    public void saveState(HotelState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(state);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public HotelState loadState() {
        File file = new File(filename);
        if (!file.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (HotelState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}