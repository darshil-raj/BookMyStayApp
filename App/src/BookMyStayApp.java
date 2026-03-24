/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates concurrent booking requests with thread-safe room allocation.
 * Synchronization ensures consistent inventory updates and prevents double-booking.
 *
 * @author YourName
 * @version 11.0
 */

import java.util.*;
import java.util.concurrent.*;

// -------------------- MAIN CLASS --------------------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v11.0 ");
        System.out.println("=======================================");

        // Initialize shared inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        // Shared booking queue
        Queue<Reservation> bookingQueue = new ConcurrentLinkedQueue<>();

        // Sample booking requests from multiple guests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Double Room"));
        bookingQueue.add(new Reservation("David", "Single Room")); // Exceeds availability

        // Create a thread pool to simulate concurrent bookings
        int threadCount = 4;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        BookingProcessor processor = new BookingProcessor(inventory, bookingQueue);

        // Submit booking tasks
        for (int i = 0; i < threadCount; i++) {
            executor.submit(processor);
        }

        // Shutdown executor and wait for completion
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted");
        }

        System.out.println("\nFinal Inventory Status:");
        inventory.printInventory();

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

// -------------------- ROOM INVENTORY --------------------
class RoomInventory {

    private final Map<String, Integer> inventory = new HashMap<>();

    public synchronized void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void printInventory() {
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type) + " rooms available");
        }
    }
}

// -------------------- BOOKING PROCESSOR --------------------
class BookingProcessor implements Runnable {

    private RoomInventory inventory;
    private Queue<Reservation> bookingQueue;

    public BookingProcessor(RoomInventory inventory, Queue<Reservation> bookingQueue) {
        this.inventory = inventory;
        this.bookingQueue = bookingQueue;
    }

    @Override
    public void run() {
        Reservation request;
        while ((request = bookingQueue.poll()) != null) {
            processBooking(request);
        }
    }

    private void processBooking(Reservation r) {
        synchronized (inventory) { // Critical section: allocate room safely
            if (inventory.allocateRoom(r.getRoomType())) {
                System.out.println("Booking Confirmed: " + r.getGuestName()
                        + " | Room Type: " + r.getRoomType());
            } else {
                System.out.println("Booking Failed (No Availability): " + r.getGuestName()
                        + " | Room Type: " + r.getRoomType());
            }
        }
    }
}