/**
 * UseCase4RoomSearch
 *
 * This class demonstrates room search functionality using read-only access
 * to inventory. It filters available rooms and displays their details
 * without modifying system state.
 *
 * @author YourName
 * @version 4.0
 */

import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v4.0 ");
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // unavailable
        inventory.addRoomType("Suite Room", 2);

        // Initialize room catalog (domain objects)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Perform search (read-only)
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nApplication terminating...");
    }
}

// -------------------- SEARCH SERVICE --------------------
class SearchService {

    /**
     * Displays only available rooms (read-only operation)
     */
    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("\n--- Available Rooms ---\n");

        for (Room room : rooms) {

            int availability = inventory.getAvailability(room.getRoomType());

            // Defensive check: only show available rooms
            if (availability > 0) {
                room.displayDetails();
                System.out.println("Available Rooms: " + availability);
                System.out.println();
            }
        }
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Read-only access method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// -------------------- DOMAIN MODEL --------------------
abstract class Room {

    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.0);
    }
}