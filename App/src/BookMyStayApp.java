/**
 * UseCase8BookingHistoryReport
 *
 * This class demonstrates booking history tracking and reporting.
 * Confirmed bookings are stored in a List to maintain order,
 * and reports are generated without modifying stored data.
 *
 * @author YourName
 * @version 8.0
 */

import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v8.0 ");
        System.out.println("=======================================");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addBooking(new Reservation("RES-1001", "Alice", "Single Room"));
        history.addBooking(new Reservation("RES-1002", "Bob", "Double Room"));
        history.addBooking(new Reservation("RES-1003", "Charlie", "Suite Room"));

        // Admin views all bookings
        history.displayAllBookings();

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateSummaryReport(history.getBookings());

        System.out.println("\nApplication terminating...");
    }
}

// -------------------- RESERVATION --------------------
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- BOOKING HISTORY --------------------
class BookingHistory {

    // List to maintain insertion order
    private List<Reservation> bookings;

    public BookingHistory() {
        bookings = new ArrayList<>();
    }

    /**
     * Add confirmed booking to history
     */
    public void addBooking(Reservation reservation) {
        bookings.add(reservation);
        System.out.println("Booking Recorded: " + reservation.getReservationId());
    }

    /**
     * Retrieve all bookings (read-only usage)
     */
    public List<Reservation> getBookings() {
        return bookings;
    }

    /**
     * Display all stored bookings
     */
    public void displayAllBookings() {

        System.out.println("\n--- Booking History ---\n");

        for (Reservation r : bookings) {
            System.out.println("Reservation ID: " + r.getReservationId() +
                    " | Guest: " + r.getGuestName() +
                    " | Room: " + r.getRoomType());
        }
    }
}

// -------------------- REPORT SERVICE --------------------
class BookingReportService {

    /**
     * Generate summary report (read-only)
     */
    public void generateSummaryReport(List<Reservation> bookings) {

        System.out.println("\n--- Booking Summary Report ---\n");

        // Count bookings per room type
        Map<String, Integer> report = new HashMap<>();

        for (Reservation r : bookings) {
            report.put(r.getRoomType(),
                    report.getOrDefault(r.getRoomType(), 0) + 1);
        }

        // Display report
        for (Map.Entry<String, Integer> entry : report.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Total Bookings: " + entry.getValue());
        }
    }
}