package Client;


/* Add imports from Libraries*/

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {

    private static final String BASE_URL = "http://localhost:8080/api";

    public static void main(String[] args) {
        /* Insantiate Scanner for inputs*/

        Scanner scanner = new Scanner(System.in);

        /* Here we create a new HTTP instance of HttpClient, used to request and receive responses.
         * Here we build the URI which is set to port 8080 based on address of local server.*/
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .build();

        /* Sends HTTP requests asynchronously, bodyHandler converts response body to a String.
         * thenAccept is called when thenApply is completed, it takes body of response which is String and prints to console.
         * join() is called to block and wait for the completion of the async operation, it ensures client doesnt exit
         * before response is received.*/

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
            /* If unable to receive request and error is thrown printing out the message of said error.*/
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        while (true) {
            System.out.println("\nAirline Management System");
            System.out.println("1. Airport/Aircraft Menu");
            System.out.println("2. Passenger Management");
            System.out.println("3. City Management");
            System.out.println("4. Exit");

            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    /* Austin Implement Aircraft/Airport Menu & Logic*/

                case 2:
                    /* Daniel Implement Passenger Menu & Logic*/

                case 3:
                    cityManagement(client, scanner);
                    break;
                case 4:
                    System.exit(0);
                    break;


                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }


    private static void getAirportsInCities(HttpClient client) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cities"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Cities and their airports:");
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cityManagement(HttpClient client, Scanner scanner) {
        while (true) {
            System.out.println("\nCity Management");
            System.out.println("1. List all cities and their airports");
            System.out.println("2. Look up city by name");
            System.out.println("3. Look up city by airport");
            System.out.println("4. Back to main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    getAirportsInCities(client);
                    break;
                case 2:
                    lookupCityByName(client, scanner);
                    break;
                case 3:
                    lookupCityByAirport(client, scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void lookupCityByName(HttpClient client, Scanner scanner) {
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cities/search?name=" + cityName))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("City details:");
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void lookupCityByAirport(HttpClient client, Scanner scanner) {
        System.out.print("Enter airport ID: ");
        String airportId = scanner.nextLine();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cities/search?airportId=" + airportId))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("City details:");
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}