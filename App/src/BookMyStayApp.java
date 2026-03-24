/**
 * UseCase7AddOnServiceSelection
 *
 * This class demonstrates how add-on services can be attached
 * to an existing reservation without modifying core booking logic.
 *
 * It uses a Map<String, List<Service>> to associate services
 * with reservation IDs and calculates total additional cost.
 *
 * @author YourName
 * @version 7.0
 */

import java.util.*;

// -------------------- MAIN CLASS --------------------
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v7.0 ");
        System.out.println("=======================================");

        // Sample reservation IDs (from previous booking system)
        String reservation1 = "RES-1001";
        String reservation2 = "RES-1002";

        // Initialize Add-On Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        Service wifi = new Service("WiFi", 10.0);
        Service breakfast = new Service("Breakfast", 20.0);
        Service spa = new Service("Spa", 50.0);

        // Add services to reservations
        manager.addService(reservation1, wifi);
        manager.addService(reservation1, breakfast);

        manager.addService(reservation2, spa);
        manager.addService(reservation2, breakfast);

        // Display services and total cost
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);

        System.out.println("\nApplication terminating...");
    }
}

// -------------------- SERVICE CLASS --------------------
class Service {

    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// -------------------- ADD-ON SERVICE MANAGER --------------------
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    /**
     * Add a service to a reservation
     */
    public void addService(String reservationId, Service service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added Service: " + service.getName() +
                " to Reservation: " + reservationId);
    }

    /**
     * Display services and total cost for a reservation
     */
    public void displayServices(String reservationId) {

        System.out.println("\n--- Services for Reservation: " + reservationId + " ---");

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        double totalCost = 0;

        for (Service s : services) {
            System.out.println("Service: " + s.getName() +
                    " | Cost: $" + s.getCost());
            totalCost += s.getCost();
        }

        System.out.println("Total Add-On Cost: $" + totalCost);
    }
}