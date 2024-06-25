package Client;


/* Import Libraries */
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Option1Methods {
    public static void airportAircraftMenu() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nAirport/Aircraft Menu");
        System.out.println("1. View all Airports");
        System.out.println("2. View all Aircraft");
        System.out.println("3. Add an Airport");
        System.out.println("4. Add an Aircraft");
        System.out.println("5. Update an Airport");
        System.out.println("6. Update an Aircraft");
        System.out.println("7. Delete an Airport");
        System.out.println("8. Delete an Aircraft");
        System.out.println("9. Back to Main Menu");

        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                viewAllAirports();
                break;
            case 2:
                viewAllAircraft();
                break;
            case 3:
                addAirport();
                break;
            case 4:
                addAircraft();
                break;
            case 5:
                // Add your method call here
                break;
            case 6:
                // Add your method call here
                break;
            case 7:
                // Add your method call here
                break;
            case 8:
                // Add your method call here
                break;
            case 9:
                // Add your method call here
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    public static void viewAllAirports() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/airports/all"))
                .build();

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void viewAllAircraft(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/aircraft"))
                .build();

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static <Aircraft> void addAirport() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Airport ID: ");
        int airportID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the city ID of the airport: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the city of the airport: ");
        String city = scanner.nextLine();
        System.out.println("Enter the State of the airport: ");
        String state = scanner.nextLine();
        System.out.println("Enter the Cities Population: ");
        int population = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/airports/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"airportID\": " + airportID + ", \"city\": { \"id\": " + id + ", \"name\": \"" + city + "\", \"state\": \"" + state + "\", \"population\": " + population + " } }"))
                .build();

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the city of the aircraft: ");
        String city = scanner.nextLine();
        System.out.println("Enter the seating capacity of the aircraft: ");
        int seating = scanner.nextInt();
        System.out.println("Enter the capacity of the aircraft: ");
        int capacity = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/aircraft"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"city\": \"" + city + "\" }"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            long aircraftId = Long.parseLong(responseBody);


            HttpRequest seatingRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/aircraft/" + aircraftId + "/seating/" + seating))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            client.send(seatingRequest, HttpResponse.BodyHandlers.ofString());


            HttpRequest capacityRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/aircraft/" + aircraftId + "/capacity"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(String.valueOf(capacity)))
                    .build();
            client.send(capacityRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}

