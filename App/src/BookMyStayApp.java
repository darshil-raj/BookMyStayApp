/**
 * UseCase2RoomInitialization
 * Demonstrates Room Types using Abstraction and Inheritance
 * Hotel Booking System v2.0
 *
 * @author Munna
 * @version 2.0
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: ₹" + price);
    }
}

/* Single Room */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 2000);
    }
}

/* Double Room */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 3500);
    }
}

/* Suite Room */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 7000);
    }
}

/* Main Application Class */
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking System v2.0 ");
        System.out.println("=================================");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details ---\n");

        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);

        System.out.println("\nApplication finished.");
    }
}
