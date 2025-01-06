package com.example.exam.Model;

import java.util.List;

public class Repas {
    private int id;
    private String nom;
    private List<Supplement> supplements;

    public Repas() {
    }

    public Repas(int id, String nom, List<Supplement> supplements) {
        this.id = id;
        this.nom = nom;
        this.supplements = supplements;
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

    public List<Supplement> getSupplements() {
        return supplements;
    }

    public void setSupplements(List<Supplement> supplements) {
        this.supplements = supplements;
    }

    @Override
    public String toString() {
        return "Repas{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", supplements=" + supplements +
                '}';
    }

    public double calculerTotal() {
        if (supplements == null || supplements.isEmpty()) {
            return 0.0;
        }
        return supplements.stream()
                .mapToDouble(Supplement::getPrix)
                .sum();
    }

}
