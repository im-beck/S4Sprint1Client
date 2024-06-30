package Client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CityManager {
    private static final String BASE_URL = "http://localhost:8080/api"; // Update the base URL as needed

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

    private static void lookupCityByName(HttpClient client, Scanner scanner) {
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine();

        try {
            String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cities/search?name=" + encodedCityName))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("City details:");
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void CityMenu(HttpClient client, Scanner scanner) {
        while (true) {
            System.out.println("\nCity Menu");
            System.out.println("1. List all cities");
            System.out.println("2. Look up city by name");
            System.out.println("3. Back to main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    getAirportsInCities(client);
                    break;
                case 2:
                    lookupCityByName(client, scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
