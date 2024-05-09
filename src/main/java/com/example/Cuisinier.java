package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cuisinier extends Utilisateur {

    public Boolean SuspendreCpt(int idCpt) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE utilisateur SET etat = 'suspendu' WHERE idCpt = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idCpt);
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}