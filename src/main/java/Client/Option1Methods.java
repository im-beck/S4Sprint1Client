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
        while (true) {
            System.out.println("\nAirport/Aircraft Menu");
            System.out.println("1. View all Airports");
            System.out.println("2. View all Aircraft");
            System.out.println("3. Add an Airport");
            System.out.println("4. Add an Aircraft");
            System.out.println("5. View an Aircraft");
            System.out.println("6. Update an Airport");
            System.out.println("7. Update an Aircraft");
            System.out.println("8. Delete an Airport");
            System.out.println("9. Delete an Aircraft");
            System.out.println("10. Add an Airport to an Aircraft");
            System.out.println("11. Exit");

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
                    viewAircraft();
                    break;
                case 6:
                    updateAirport();
                    break;
                case 7:
                    updateAircraft();
                    break;
                case 8:
                    deleteAirport();
                    break;
                case 9:
                    deleteAircraft();
                    break;
                case 10:
                    addAirportToAircraft();
                    break;
                case 11:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    /**
     * This method is used to view all airports in the system.
     * It sends a GET request to the server to retrieve all airports.
     * The server's response is printed to the console.
     * If an error occurs during the process, it is caught and the error message is printed to the console.
     */
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

    /**
     * This method is used to add a new airport to the system.
     * It prompts the user to enter the name, city, state, and population of the airport.
     * It then generates unique IDs for the airport and the city.
     * A POST request is sent to the server to add the airport with the entered details.
     * The server's response is printed to the console.
     * If an error occurs during the process, it is caught and the error message is printed to the console.
     */
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
                .POST(HttpRequest.BodyPublishers.ofString("{ \"airportID\": " + airportID + ", \"name\": \"" + name + "\", \"city\": { \"id\": " + CITY_ID  + ", \"name\": \"" + city + "\", \"state\": \"" + state + "\", \"population\": " + population + " } }"))
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

    /**
     * This method is used to generate a unique ID for an airport.
     * It increments a static variable AIRPORT_ID by 1 each time it's called.
     * @return the new unique airport ID.
     */
    public static int generateUniqueAirportID() {
        AIRPORT_ID+= 6;


        return AIRPORT_ID;
    }
    /**
     * This method is used to generate a unique ID for a city.
     * It increments a static variable CITY_ID by 1 each time it's called.
     * @return the new unique city ID.
     */
    public static int generateUniqueCityID() {
        CITY_ID+= 6;
        return CITY_ID;
    }

    /**
     * This method is used to add a new aircraft to the system.
     * It prompts the user to enter the airport ID, city, seating capacity, and capacity of the aircraft.
     * It then sends a POST request to the server to add the aircraft with the entered details.
     * The server's response is printed to the console.
     * If the response contains an 'aircraftID', it is extracted and printed to the console.
     */
    public static void addAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the airport ID: ");
        int airportID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the city of the aircraft: ");
        String city = scanner.nextLine();
        System.out.println("Enter the seating capacity of the aircraft: ");
        int seating = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the capacity of the aircraft: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();


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
                Number num = (Number) aircraftMap.get("aircraftID");
                long aircraftId = num.longValue();
                System.out.println("Aircraft added successfully. Aircraft ID: " + aircraftId);
            } else {
                System.out.println("The key 'aircraftID' does not exist in the map.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    public static void viewAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Aircraft ID: ");
        int aircraftID = scanner.nextInt();
        scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/aircraft/" + aircraftID))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body().trim();

            System.out.println(responseBody);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method is used to update an existing airport in the system.
     * It prompts the user to enter the airport ID, new name, new city, new state, and new population of the city.
     * It then sends a PUT request to the server to update the airport with the new details.
     * The server's response is printed to the console.
     */
    public static void updateAirport() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Airport ID: ");
        int airportID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new name of the airport: ");
        String name = scanner.nextLine();
        System.out.println("Enter the new city of the airport: ");
        String city = scanner.nextLine();
        System.out.println("Enter the new state of the airport: ");
        String state = scanner.nextLine();
        System.out.println("Enter the new population of the city: ");
        int population = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/airports/update/" + airportID))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("{ \"airportID\": " + airportID + ", \"name\": \"" + name + "\", \"city\": { \"id\": " + CITY_ID  + ", \"name\": \"" + city + "\", \"state\": \"" + state + "\", \"population\": " + population + " } }"))
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

    /**
     * This method is used to update an existing aircraft in the system.
     * It prompts the user to enter the aircraft ID, new city, new seating capacity, and new capacity of the aircraft.
     * It then sends a PUT request to the server to update the aircraft with the new details.
     * The server's response is printed to the console.
     */
    public static void updateAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Aircraft ID: ");
        int aircraftID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new city of the aircraft: ");
        String city = scanner.nextLine();
        System.out.println("Enter the new seating capacity of the aircraft: ");
        int seating = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new capacity of the aircraft: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/aircraft/update/" + aircraftID))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("{ \"city\": \"" + city + "\", \"seating\": " + seating + ", \"capacity\": " + capacity + " }"))
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

    public static void deleteAirport() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Airport ID: ");
        int airportID = scanner.nextInt();
        scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/airports/" + airportID))
                .DELETE()
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

    public static void deleteAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Aircraft ID: ");
        int aircraftID = scanner.nextInt();
        scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/aircraft/" + aircraftID))
                .DELETE()
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

public static void addAirportToAircraft() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Aircraft ID: ");
        int aircraftID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the Airport ID: ");
        int airportID = scanner.nextInt();
        scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/aircraft/" + aircraftID + "/airports"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"airportID\": " + airportID + " }"))
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

