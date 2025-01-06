package com.example.exam.DAO;

import com.example.exam.Model.Supplement;
import com.example.exam.Singleton.SingletonConnexionDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class SupplementDAO {
    Connection connection = SingletonConnexionDb.getConnexion();

    public void create(Supplement supplement) throws SQLException {
        String query = "INSERT INTO supplement (nom, prix) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, supplement.getNom());
            preparedStatement.setDouble(2, supplement.getPrix());
            preparedStatement.executeUpdate();
        }
    }

    public Supplement read(int id) throws SQLException {
        String query = "SELECT * FROM supplement WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Supplement supplement = new Supplement();
                    supplement.setId(resultSet.getInt("id"));
                    supplement.setNom(resultSet.getString("nom"));
                    supplement.setPrix(resultSet.getDouble("prix"));
                    return supplement;
                }
            }
        }
        return null;
    }

    public void update(Supplement supplement) throws SQLException {
        String query = "UPDATE supplement SET nom = ?, prix = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, supplement.getNom());
            preparedStatement.setDouble(2, supplement.getPrix());
            preparedStatement.setInt(3, supplement.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Supplement WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<Supplement> readAll() throws SQLException {
        String query = "SELECT * FROM supplement";
        List<Supplement> supplements = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Supplement supplement = new Supplement();
                supplement.setId(resultSet.getInt("id"));
                supplement.setNom(resultSet.getString("nom"));
                supplement.setPrix(resultSet.getDouble("prix"));
                supplements.add(supplement);
            }
        }
        return supplements;
    }

}
