package Client;


/* Add imports from Libraries*/

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


/* Libraries*/


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
            System.out.println("Airline Management System");
            System.out.println("1. Airport/Aircraft Menu");
            System.out.println("2. Passenger Management");
            System.out.println("3. City Menu");
            System.out.println("4. Exit");

            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Option1Methods.airportAircraftMenu();
                    break;

                case 2:
                    PassengerMenu.passengerMenu();
                    break;

                case 3:
                    CityManager.CityMenu(client, scanner);
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
}


