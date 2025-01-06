package com.example.exam.Model;

import java.util.Map;

public class PlatPrincipal {
    private int id;
    private String nom;
    private Map<Ingredient,Double> ingredients;

    public PlatPrincipal() {
    }

    public PlatPrincipal(int id, String nom, Map<Ingredient, Double> ingredients) {
        this.id = id;
        this.nom = nom;
        this.ingredients = ingredients;
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

    public Map<Ingredient, Double> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, Double> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "PlatPrincipal{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
