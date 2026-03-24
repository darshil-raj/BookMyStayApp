/**
 * UseCase2RoomInitialization
 *
 * This class demonstrates object modeling using abstraction, inheritance,
 * and polymorphism in a Hotel Booking System.
 *
 * It initializes different room types and displays their details along
 * with static availability.
 *
 * @author YourName
 * @version 2.1
 */
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v2.1 ");
        System.out.println("=======================================");

        // Creating room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability (simple variables)
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display room details
        System.out.println("\n--- Room Details & Availability ---\n");

        singleRoom.displayDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);
        System.out.println();

        suiteRoom.displayDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
        System.out.println();

        System.out.println("Application terminating...");
    }
}

/**
 * Abstract class representing a generic Room
 */
abstract class Room {

    // Encapsulated attributes
    private int numberOfBeds;
    private double price;
    private String roomType;

    // Constructor
    public Room(String roomType, int numberOfBeds, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }

    // Method to display common details
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Price per Night: $" + price);
    }
}

/**
 * Single Room class
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

/**
 * Double Room class
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

/**
 * Suite Room class
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 300.0);
    }
}