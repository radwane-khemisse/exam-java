package com.example.exam.Console;

import com.example.exam.DAO.*;
import com.example.exam.Model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClientDAO clientDAO = new ClientDAO();
        SupplementDAO supplementDAO = new SupplementDAO();
        RepasDAO repasDAO = new RepasDAO();
        CommandeDAO commandeDAO = new CommandeDAO();

        try {
            // Ajouter un client
            System.out.print("Entrez le nom du client: ");
            String clientName = scanner.nextLine();
            System.out.print("Entrez l'email du client: ");
            String clientEmail = scanner.nextLine();
            Client client = new Client();
            client.setNom(clientName);
            client.setEmail(clientEmail);
            clientDAO.createClient(client);
            System.out.println("Client ajouté avec succès!");

            // Ajouter des suppléments
            Supplement frites = new Supplement();
            frites.setNom("Frites");
            frites.setPrix(11.0);
            supplementDAO.create(frites);

            Supplement boisson = new Supplement();
            boisson.setNom("Boisson");
            boisson.setPrix(12.0);
            supplementDAO.create(boisson);

            Supplement jusOrange = new Supplement();
            jusOrange.setNom("Jus d'orange");
            jusOrange.setPrix(13.0);
            supplementDAO.create(jusOrange);

            Supplement salade = new Supplement();
            salade.setNom("Salade marocaine");
            salade.setPrix(14.0);
            supplementDAO.create(salade);

            System.out.println("Suppléments ajoutés avec succès!");

            // Créer des repas
            Repas repas1 = new Repas();
            repas1.setNom("Tajine de viande & Pruneaux");
            repas1.setSupplements(List.of(frites, boisson));
            repasDAO.create(repas1);

            Repas repas2 = new Repas();
            repas2.setNom("Tajine de poulet & légumes");
            repas2.setSupplements(List.of(jusOrange, salade));
            repasDAO.create(repas2);

            System.out.println("Repas ajoutés avec succès!");

            // Créer une commande
            Commande commande = new Commande();
            commande.setClient(client);
            commande.setRepas(List.of(repas1, repas2));
            commandeDAO.create(commande);
            System.out.println("Commande ajoutée avec succès!");

            // Afficher le ticket
            System.out.println("----------------------------------");
            System.out.println("Bienvenue " + client.getNom());
            System.out.println("---------------TICKET---------------");
            System.out.println("Nom: " + client.getNom());
            System.out.println("Nombre de repas: " + commande.getRepas().size());
            double totalCommande = 0;

            int i = 1;
            for (Repas repas : commande.getRepas()) {
                System.out.println("Repas N°" + i++ + ": " + repas.getNom());
                System.out.println("Suppléments:");
                for (Supplement supplement : repas.getSupplements()) {
                    System.out.println(supplement.getNom() + ": " + supplement.getPrix());
                }
                double totalRepas = repas.calculerTotal();
                System.out.println("Total: " + totalRepas);
                totalCommande += totalRepas;
                System.out.println("************************************");
            }
            System.out.println("--Total: " + totalCommande);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}