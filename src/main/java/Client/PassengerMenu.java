package Client;

import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PassengerMenu {
    public static void passengerMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nPassenger Management");
            System.out.println("1. Add Passenger");
            System.out.println("2. Update Passenger");
            System.out.println("3. Delete Passenger");
            System.out.println("4. Search Passenger");
            System.out.println("5. Display all Passengers");
            System.out.println("6. Display all aircraft a passenger has been on");
            System.out.println("7. Add aircraft to passenger");
            System.out.println("8. Add airport to passenger");
            System.out.println("9. display all airports a passenger has been to");
            System.out.println("10. delete aircraft from passenger");
            System.out.println("11. delete airport from passenger");
            System.out.println("12. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPassenger();
                    break;
                case 2:
                    updatePassenger();
                    break;
                case 3:
                    deletePassenger();
                    break;
                case 4:
                    searchPassenger();
                    break;
                case 5:
                    displayAllPassengers();
                    break;
                case 6:
                    displayAllAircraftPassenger();
                    break;
                case 7:
                    addAircraftPassenger();
                    break;
                case 8:
                    addAirportPassenger();
                    break;
                case 9:
                    displayAllAirportsPassenger();
                    break;
                case 10:
                    deleteAircraftPassenger();
                    break;
                case 11:
                    deleteAirportPassenger();
                    break;
                case 12:
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

        public static void addPassenger() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Passenger First Name: ");
            String firstName = scanner.next();
            System.out.println("Enter Passenger Last Name: ");
            String lastName = scanner.next();
            System.out.println("Enter Passenger Phone Number: ");
            String phoneNumber = scanner.next();
            System.out.println("Enter Passenger City: ");
            String city = scanner.next();
            String JSON = "{\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\",\"phoneNumber\":\"" + phoneNumber + "\",\"city\":\"" + city + "\"}";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/passengers"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(JSON))
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

    public static void updatePassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long id = scanner.nextLong();
        System.out.println("Enter Passenger First Name: ");
        String firstName = scanner.next();
        System.out.println("Enter Passenger Last Name: ");
        String lastName = scanner.next();
        System.out.println("Enter Passenger Phone Number: ");
        String phoneNumber = scanner.next();
        System.out.println("Enter Passenger City: ");
        String city = scanner.next();
        String JSON = "{\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\",\"phoneNumber\":\"" + phoneNumber + "\",\"city\":\"" + city + "\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(JSON))
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

    public static void deletePassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long id = scanner.nextLong();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + id))
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

    public static void searchPassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long id = scanner.nextLong();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + id))
                .GET()
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

    public static void displayAllPassengers() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers"))
                .GET()
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

    public static void displayAllAircraftPassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long id = scanner.nextLong();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + id + "/aircraft"))
                .GET()
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

    public static void addAircraftPassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long passengerId = scanner.nextLong();
        System.out.println("Enter Aircraft ID: ");
        Long aircraftId = scanner.nextLong();
        String JSON = "{\"aircraftId\":\"" + aircraftId + "\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + passengerId + "/aircraft/" + aircraftId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON))
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

    public static void addAirportPassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long passengerId = scanner.nextLong();
        System.out.println("Enter Airport ID: ");
        Long airportId = scanner.nextLong();
        String JSON = "{\"airportId\":\"" + airportId + "\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + passengerId + "/airport/" + airportId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON))
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

    public static void displayAllAirportsPassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long id = scanner.nextLong();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + id + "/airport"))
                .GET()
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

    public static void deleteAircraftPassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long passengerId = scanner.nextLong();
        System.out.println("Enter Aircraft ID: ");
        Long aircraftId = scanner.nextLong();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + passengerId + "/aircraft/" + aircraftId))
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

    public static void deleteAirportPassenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger ID: ");
        Long passengerId = scanner.nextLong();
        System.out.println("Enter Airport ID: ");
        Long airportId = scanner.nextLong();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/passengers/" + passengerId + "/airport/" + airportId))
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
}
