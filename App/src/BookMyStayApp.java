/**
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

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v3.1 ");
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

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
    }

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
    }

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
    }
}