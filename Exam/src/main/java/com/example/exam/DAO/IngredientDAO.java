package com.example.exam.DAO;

import com.example.exam.Model.Ingredient;
import com.example.exam.Singleton.SingletonConnexionDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    Connection connection = SingletonConnexionDb.getConnexion();

    public void create(Ingredient ingredient) throws SQLException {
        String query = "INSERT INTO ingredient (nom, prix) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ingredient.getNom());
            preparedStatement.setString(2, ingredient.getPrix());
            preparedStatement.executeUpdate();
        }
    }

    public Ingredient read(int id) throws SQLException {
        String query = "SELECT * FROM ingredient WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(resultSet.getInt("id"));
                    ingredient.setNom(resultSet.getString("nom"));
                    ingredient.setPrix(resultSet.getString("prix"));
                    return ingredient;
                }
            }
        }
        return null;
    }

    public void update(Ingredient ingredient) throws SQLException {
        String query = "UPDATE ingredient SET nom = ?, prix = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ingredient.getNom());
            preparedStatement.setString(2, ingredient.getPrix());
            preparedStatement.setInt(3, ingredient.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM ingredient WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<Ingredient> readAll() throws SQLException {
        String query = "SELECT * FROM ingredient";
        List<Ingredient> ingredients = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setNom(resultSet.getString("nom"));
                ingredient.setPrix(resultSet.getString("prix"));
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }


}
