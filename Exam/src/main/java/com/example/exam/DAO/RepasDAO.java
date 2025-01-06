package com.example.exam.DAO;

import com.example.exam.Model.Repas;
import com.example.exam.Model.Supplement;
import com.example.exam.Singleton.SingletonConnexionDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepasDAO {
    Connection connection = SingletonConnexionDb.getConnexion();

    public void create(Repas repas) throws SQLException {
        String query = "INSERT INTO repas (nom) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, repas.getNom());
            preparedStatement.executeUpdate();

            if (repas.getSupplements() != null) {
                for (Supplement supplement : repas.getSupplements()) {
                    String supplementQuery = "INSERT INTO repas_supplement (repas_id, supplement_id) VALUES (?, ?)";
                    try (PreparedStatement supplementStmt = connection.prepareStatement(supplementQuery)) {
                        supplementStmt.setInt(1, repas.getId());
                        supplementStmt.setInt(2, supplement.getId());
                        supplementStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public Repas read(int id) throws SQLException {
        String query = "SELECT * FROM repas WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Repas repas = new Repas();
                    repas.setId(resultSet.getInt("id"));
                    repas.setNom(resultSet.getString("nom"));

                    String supplementsQuery = "SELECT s.id, s.nom, s.prix FROM supplement s " +
                            "JOIN Repas_Supplements rs ON s.id = rs.supplement_id WHERE rs.repas_id = ?";
                    try (PreparedStatement supplementsStmt = connection.prepareStatement(supplementsQuery)) {
                        supplementsStmt.setInt(1, id);
                        try (ResultSet supplementsResultSet = supplementsStmt.executeQuery()) {
                            List<Supplement> supplements = new ArrayList<>();
                            while (supplementsResultSet.next()) {
                                Supplement supplement = new Supplement();
                                supplement.setId(supplementsResultSet.getInt("id"));
                                supplement.setNom(supplementsResultSet.getString("nom"));
                                supplement.setPrix(supplementsResultSet.getDouble("prix"));
                                supplements.add(supplement);
                            }
                            repas.setSupplements(supplements);
                        }
                    }
                    return repas;
                }
            }
        }
        return null;
    }

    public void update(Repas repas) throws SQLException {
        String query = "UPDATE repas SET nom = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, repas.getNom());
            preparedStatement.setInt(2, repas.getId());
            preparedStatement.executeUpdate();

            String deleteSupplementsQuery = "DELETE FROM repas_Supplement WHERE repas_id = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSupplementsQuery)) {
                deleteStmt.setInt(1, repas.getId());
                deleteStmt.executeUpdate();
            }

            if (repas.getSupplements() != null) {
                for (Supplement supplement : repas.getSupplements()) {
                    String supplementQuery = "INSERT INTO repas_supplements (repas_id, supplement_id) VALUES (?, ?)";
                    try (PreparedStatement supplementStmt = connection.prepareStatement(supplementQuery)) {
                        supplementStmt.setInt(1, repas.getId());
                        supplementStmt.setInt(2, supplement.getId());
                        supplementStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        String deleteSupplementsQuery = "DELETE FROM repas_supplements WHERE repas_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSupplementsQuery)) {
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
        }

        String query = "DELETE FROM repas WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<Repas> readAll() throws SQLException {
        String query = "SELECT * FROM eepas";
        List<Repas> repasList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Repas repas = new Repas();
                repas.setId(resultSet.getInt("id"));
                repas.setNom(resultSet.getString("nom"));

                String supplementsQuery = "SELECT s.id, s.nom, s.prix FROM supplement s " +
                        "JOIN repas_supplement rs ON s.id = rs.supplement_id WHERE rs.repas_id = ?";
                try (PreparedStatement supplementsStmt = connection.prepareStatement(supplementsQuery)) {
                    supplementsStmt.setInt(1, repas.getId());
                    try (ResultSet supplementsResultSet = supplementsStmt.executeQuery()) {
                        List<Supplement> supplements = new ArrayList<>();
                        while (supplementsResultSet.next()) {
                            Supplement supplement = new Supplement();
                            supplement.setId(supplementsResultSet.getInt("id"));
                            supplement.setNom(supplementsResultSet.getString("nom"));
                            supplement.setPrix(supplementsResultSet.getDouble("prix"));
                            supplements.add(supplement);
                        }
                        repas.setSupplements(supplements);
                    }
                }
                repasList.add(repas);
            }
        }
        return repasList;
    }

}
