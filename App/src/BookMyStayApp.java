/**
 feature/UC4-RoomSearch
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
 * UseCase3InventorySetup
 *
 * This class demonstrates centralized room inventory management using HashMap.
 * It replaces scattered availability variables with a single source of truth.
 *
 * @author YourName
 * @version 3.1
 */
import java.util.HashMap;
import java.util.Map;

public class UseCase3InventorySetup {
 dev

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
 feature/UC4-RoomSearch
        System.out.println(" Hotel Booking System v4.0 ");

        System.out.println(" Hotel Booking System v3.1 ");
 dev
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
feature/UC4-RoomSearch
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


        // Register room types with availability
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display current inventory
        System.out.println("\n--- Current Room Inventory ---");
        inventory.displayInventory();

        // Update availability
        System.out.println("\n--- Updating Inventory ---");
        inventory.updateAvailability("Single Room", 4); // example update
        inventory.updateAvailability("Suite Room", 1);

        // Display updated inventory
        System.out.println("\n--- Updated Room Inventory ---");
        inventory.displayInventory();

        System.out.println("\nApplication terminating...");
    }
}

/**
 * RoomInventory class
 *
 * Manages room availability using a centralized HashMap.
 */
class RoomInventory {

    // HashMap to store room type -> availability
    private Map<String, Integer> inventory;

    /**
     * Constructor initializes the inventory
     */
    public RoomInventory() {
        inventory = new HashMap<>();
dev
    }

feature/UC4-RoomSearch
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);

    /**
     * Adds a new room type to the inventory
     */
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Retrieves availability for a given room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
 dev
    }

 feature/UC4-RoomSearch
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.0);

    /**
     * Updates availability for a given room type
     */
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    /**
     * Displays all room inventory
     */
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Available: " + entry.getValue());
        }
 dev
    }
}