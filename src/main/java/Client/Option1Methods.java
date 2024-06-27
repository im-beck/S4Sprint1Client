package Client;


/* Import Libraries */
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Option1Methods {

    private static int AIRPORT_ID = 0;
    private static int CITY_ID = 0;
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

    public static void addAirport() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the airport: ");
        String name = scanner.nextLine();
        System.out.println("Enter the city of the airport: ");
        String city = scanner.nextLine();
        System.out.println("Enter the State of the airport: ");
        String state = scanner.nextLine();
        System.out.println("Enter the Cities Population: ");
        int population = scanner.nextInt();

        // Generate a new unique ID for the airport
        int airportID = generateUniqueAirportID();
        int cityID = generateUniqueCityID();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/airports/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"airportID\": " + AIRPORT_ID + ", \"name\": \"" + name + "\", \"city\": { \"id\": " + CITY_ID  + ", \"name\": \"" + city + "\", \"state\": \"" + state + "\", \"population\": " + population + " } }"))
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


    public static int generateUniqueAirportID() {
        AIRPORT_ID+= 1;


        return AIRPORT_ID;
    }

    public static int generateUniqueCityID() {
        CITY_ID+= 1;
        return CITY_ID;
    }

    public static void addAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the airport ID: ");
        int airportID = scanner.nextInt();
        System.out.println("Enter the Aircraft ID: ");

        scanner.nextLine();
        System.out.println("Enter the city of the aircraft: ");
        String city = scanner.nextLine();
        System.out.println("Enter the seating capacity of the aircraft: ");
        int seating = scanner.nextInt();
        System.out.println("Enter the capacity of the aircraft: ");
        int capacity = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/aircraft/" + airportID))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"city\": \"" + city + "\", \"seating\": " + seating + ", \"capacity\": " + capacity + " }"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println(responseBody);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> aircraftMap = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>(){});
            if (aircraftMap.containsKey("aircraftID")) {
                Long aircraftId = (Long) aircraftMap.get("aircraftID");
                System.out.println("Aircraft added successfully. Aircraft ID: " + aircraftId);
            } else {
                System.out.println("The key 'aircraftID' does not exist in the map.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateAirport() {
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
                .uri(URI.create("http://localhost:8080/api/airports/update"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("{ \"airportID\": " + airportID + ", \"city\": { \"id\": " + id + ", \"name\": \"" + city + "\", \"state\": \"" + state + "\", \"population\": " + population + " } }"))
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

    

}

