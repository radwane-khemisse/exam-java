package com.example.exam.Console;

import com.example.exam.DAO.ClientDAO;
import com.example.exam.Model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ClientConsole {
    public static void main(String[] args) {
        ClientDAO clientDAO = new ClientDAO();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Client Management Console ---");
            System.out.println("1. Add a new client");
            System.out.println("2. View a client by ID");
            System.out.println("3. Update a client");
            System.out.println("4. Delete a client");
            System.out.println("5. View all clients");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter client ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter client name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter client email: ");
                    String email = scanner.nextLine();

                    Client newClient = new Client(id, name, email, null);
                    clientDAO.createClient(newClient);
                    System.out.println("Client added successfully.");
                    break;

                case 2:
                    System.out.print("Enter client ID: ");
                    int clientId = scanner.nextInt();
                    try {
                        Client client = clientDAO.read(clientId);
                        if (client != null) {
                            System.out.println(client);
                        } else {
                            System.out.println("Client not found.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving client: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Enter client ID: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new client name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new client email: ");
                    String newEmail = scanner.nextLine();

                    Client updatedClient = new Client(updateId, newName, newEmail, null);
                    try {
                        clientDAO.updateClient(updatedClient);
                        System.out.println("Client updated successfully.");
                    } catch (SQLException e) {
                        System.out.println("Error updating client: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter client ID to delete: ");
                    int deleteId = scanner.nextInt();
                    try {
                        clientDAO.deleteClient(deleteId);
                        System.out.println("Client deleted successfully.");
                    } catch (SQLException e) {
                        System.out.println("Error deleting client: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        List<Client> clients = clientDAO.getAllClients();
                        if (!clients.isEmpty()) {
                            clients.forEach(System.out::println);
                        } else {
                            System.out.println("No clients found.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving clients: " + e.getMessage());
                    }
                    break;

                case 6:
                    running = false;
                    System.out.println("Exiting Client Management Console. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}

