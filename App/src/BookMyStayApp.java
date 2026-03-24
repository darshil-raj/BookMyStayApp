/**
 * UseCase5BookingRequestQueue
 *
 * This class demonstrates handling booking requests using a Queue
 * to ensure First-Come-First-Served (FIFO) processing.
 *
 * Booking requests are collected and stored without modifying inventory.
 *
 * @author YourName
 * @version 5.0
 */

import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v5.0 ");
        System.out.println("=======================================");

        // Initialize booking queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("Diana", "Single Room"));

        // Display queued requests
        requestQueue.displayQueue();

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

// -------------------- BOOKING QUEUE --------------------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    /**
     * Add booking request to queue (FIFO)
     */
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: "
                + reservation.getGuestName() + " -> "
                + reservation.getRoomType());
    }

    /**
     * Display all queued requests (without removing)
     */
    public void displayQueue() {

        System.out.println("\n--- Booking Request Queue (FIFO Order) ---\n");

        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName() +
                    " | Room: " + r.getRoomType());
        }
    }
}