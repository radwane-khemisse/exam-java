package com.example.exam.Model;

import java.util.List;

public class Client {
    private int id;
    private String nom;
    private String email;
    private List<Commande> commandes;

    public Client() {
    }

    public Client(int id, String nom, String email, List<Commande> commandes) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.commandes = commandes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", commandes=" + commandes +
                '}';
    }
}
