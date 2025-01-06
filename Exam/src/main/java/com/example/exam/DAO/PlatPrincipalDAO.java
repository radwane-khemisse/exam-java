package com.example.exam.DAO;

import com.example.exam.Model.Client;
import com.example.exam.Model.Ingredient;
import com.example.exam.Model.PlatPrincipal;
import com.example.exam.Singleton.SingletonConnexionDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PlatPrincipalDAO {

    Connection conn = SingletonConnexionDb.getConnexion();

    public void create(PlatPrincipal platPrincipal) throws SQLException {
        String query = "INSERT INTO platprincipal (nom) VALUES (?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, platPrincipal.getNom());
            preparedStatement.executeUpdate();

            if (platPrincipal.getIngredients() != null) {
                String ingredientQuery = "INSERT INTO platprincipal_ingredient (plat_principal_id, ingredient_id, quantite) VALUES (?, ?, ?)";
                try (PreparedStatement ingredientStmt = conn.prepareStatement(ingredientQuery)) {
                    for (Map.Entry<Ingredient, Double> entry : platPrincipal.getIngredients().entrySet()) {
                        ingredientStmt.setInt(1, platPrincipal.getId());
                        ingredientStmt.setInt(2, entry.getKey().getId());
                        ingredientStmt.setDouble(3, entry.getValue());
                        ingredientStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public PlatPrincipal read(int id) throws SQLException {
        String query = "SELECT * FROM platprincipal WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    PlatPrincipal platPrincipal = new PlatPrincipal();
                    platPrincipal.setId(resultSet.getInt("id"));
                    platPrincipal.setNom(resultSet.getString("nom"));

                    // Retrieve ingredients
                    String ingredientQuery = "SELECT * FROM PlatPrincipal_Ingredient WHERE plat_principal_id = ?";
                    try (PreparedStatement ingredientStmt = conn.prepareStatement(ingredientQuery)) {
                        ingredientStmt.setInt(1, id);
                        try (ResultSet ingredientResultSet = ingredientStmt.executeQuery()) {
                            Map<Ingredient, Double> ingredients = new HashMap<>();
                            IngredientDAO ingredientDAO = new IngredientDAO();
                            while (ingredientResultSet.next()) {
                                Ingredient ingredient = ingredientDAO.read(ingredientResultSet.getInt("ingredient_id"));
                                double quantity = ingredientResultSet.getDouble("quantite");
                                ingredients.put(ingredient, quantity);
                            }
                            platPrincipal.setIngredients(ingredients);
                        }
                    }
                    return platPrincipal;
                }
            }
        }
        return null;
    }

    public void update(PlatPrincipal platPrincipal) throws SQLException {
        String query = "UPDATE platprincipal SET nom = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, platPrincipal.getNom());
            preparedStatement.setInt(2, platPrincipal.getId());
            preparedStatement.executeUpdate();

            // Update ingredients
            String deleteIngredientsQuery = "DELETE FROM platprincipal_ingredient WHERE plat_principal_id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteIngredientsQuery)) {
                deleteStmt.setInt(1, platPrincipal.getId());
                deleteStmt.executeUpdate();
            }

            if (platPrincipal.getIngredients() != null) {
                String ingredientQuery = "INSERT INTO platprincipal_ingredient (plat_principal_id, ingredient_id, quantite) VALUES (?, ?, ?)";
                try (PreparedStatement ingredientStmt = conn.prepareStatement(ingredientQuery)) {
                    for (Map.Entry<Ingredient, Double> entry : platPrincipal.getIngredients().entrySet()) {
                        ingredientStmt.setInt(1, platPrincipal.getId());
                        ingredientStmt.setInt(2, entry.getKey().getId());
                        ingredientStmt.setDouble(3, entry.getValue());
                        ingredientStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        String ingredientQuery = "DELETE FROM platprincipal_ingredient WHERE plat_principal_id = ?";
        try (PreparedStatement ingredientStmt = conn.prepareStatement(ingredientQuery)) {
            ingredientStmt.setInt(1, id);
            ingredientStmt.executeUpdate();
        }

        String query = "DELETE FROM platprincipal WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }




}
