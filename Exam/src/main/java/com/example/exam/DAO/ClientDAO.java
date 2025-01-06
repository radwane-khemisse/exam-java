package com.example.exam.DAO;

import com.example.exam.Model.Client;
import com.example.exam.Singleton.SingletonConnexionDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    Connection conn = SingletonConnexionDb.getConnexion();

    public void createClient(Client client){
        String query = "INSERT INTO client (nom, email) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, client.getNom());
            ps.setString(2, client.getEmail());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client read(int id) throws SQLException {
        String query = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        new ArrayList<>()
                );
            }
        }
        return null;
    }

    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE client SET nom = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, client.getNom());
            statement.setString(2, client.getEmail());
            statement.setInt(3, client.getId());
            statement.executeUpdate();
        }
    }

    public void deleteClient(int id) throws SQLException {
        String query = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        new ArrayList<>()));
            }
        }
        return clients;
    }

}
