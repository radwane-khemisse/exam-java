package com.example.exam.DAO;

import com.example.exam.Singleton.SingletonConnexionDb;
import com.example.exam.Model.Commande;
import com.example.exam.Model.Repas;
import com.example.exam.Model.Client;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {
    Connection connection = SingletonConnexionDb.getConnexion();

    public void create(Commande commande) throws SQLException {
        String query = "INSERT INTO commande (client_id) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commande.getClient().getId());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    commande.setId(generatedKeys.getInt(1));
                }
            }

            if (commande.getRepas() != null) {
                for (Repas repas : commande.getRepas()) {
                    String repasQuery = "INSERT INTO commande_repas (commande_id, repas_id) VALUES (?, ?)";
                    try (PreparedStatement repasStmt = connection.prepareStatement(repasQuery)) {
                        repasStmt.setInt(1, commande.getId());
                        repasStmt.setInt(2, repas.getId());
                        repasStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public Commande read(int id) throws SQLException {
        String query = "SELECT * FROM commande WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Commande commande = new Commande();
                    commande.setId(resultSet.getInt("id"));

                    int clientId = resultSet.getInt("client_id");
                    ClientDAO clientDAO = new ClientDAO();
                    Client client = clientDAO.read(clientId);
                    commande.setClient(client);

                    String repasQuery = "SELECT r.id, r.nom FROM repas r " +
                            "JOIN commande_repas cr ON r.id = cr.repas_id WHERE cr.commande_id = ?";
                    try (PreparedStatement repasStmt = connection.prepareStatement(repasQuery)) {
                        repasStmt.setInt(1, id);
                        try (ResultSet repasResultSet = repasStmt.executeQuery()) {
                            List<Repas> repasList = new ArrayList<>();
                            while (repasResultSet.next()) {
                                Repas repas = new Repas();
                                repas.setId(repasResultSet.getInt("id"));
                                repas.setNom(repasResultSet.getString("nom"));
                                repasList.add(repas);
                            }
                            commande.setRepas(repasList);
                        }
                    }
                    return commande;
                }
            }
        }
        return null;
    }

    public void update(Commande commande) throws SQLException {
        String deleteRepasQuery = "DELETE FROM commande_repas WHERE commande_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteRepasQuery)) {
            deleteStmt.setInt(1, commande.getId());
            deleteStmt.executeUpdate();
        }

        if (commande.getRepas() != null) {
            for (Repas repas : commande.getRepas()) {
                String repasQuery = "INSERT INTO commande_repas (commande_id, repas_id) VALUES (?, ?)";
                try (PreparedStatement repasStmt = connection.prepareStatement(repasQuery)) {
                    repasStmt.setInt(1, commande.getId());
                    repasStmt.setInt(2, repas.getId());
                    repasStmt.executeUpdate();
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        String deleteRepasQuery = "DELETE FROM commande_repas WHERE commande_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteRepasQuery)) {
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
        }

        String query = "DELETE FROM commande WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<Commande> readAll() throws SQLException {
        String query = "SELECT * FROM commande";
        List<Commande> commandes = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.setId(resultSet.getInt("id"));

                int clientId = resultSet.getInt("client_id");
                ClientDAO clientDAO = new ClientDAO();
                Client client = clientDAO.read(clientId);
                commande.setClient(client);

                String repasQuery = "SELECT r.id, r.nom FROM repas r " +
                        "JOIN commande_repas cr ON r.id = cr.repas_id WHERE cr.commande_id = ?";
                try (PreparedStatement repasStmt = connection.prepareStatement(repasQuery)) {
                    repasStmt.setInt(1, commande.getId());
                    try (ResultSet repasResultSet = repasStmt.executeQuery()) {
                        List<Repas> repasList = new ArrayList<>();
                        while (repasResultSet.next()) {
                            Repas repas = new Repas();
                            repas.setId(repasResultSet.getInt("id"));
                            repas.setNom(repasResultSet.getString("nom"));
                            repasList.add(repas);
                        }
                        commande.setRepas(repasList);
                    }
                }
                commandes.add(commande);
            }
        }
        return commandes;
    }
}
