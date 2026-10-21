package com.vetclinic.dao;

import com.vetclinic.model.ScenarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public ScenarioDao() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS pet_vaccinations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    pet_name TEXT NOT NULL,
                    species TEXT NOT NULL,
                    vaccine TEXT NOT NULL,
                    fee REAL NOT NULL CHECK (fee >= 0)
                )
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Database setup error: " + e.getMessage());
        }
    }

    public boolean addRecord(ScenarioEntity pet) {
        String sql = """
                INSERT INTO pet_vaccinations (pet_name, species, vaccine, fee)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pet.petName());
            statement.setString(2, pet.species());
            statement.setString(3, pet.vaccine());
            statement.setDouble(4, pet.fee());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Database insert error: " + e.getMessage());
            return false;
        }
    }

    public List<ScenarioEntity> getAllRecords() {
        List<ScenarioEntity> records = new ArrayList<>();
        String sql = """
                SELECT id, pet_name, species, vaccine, fee
                FROM pet_vaccinations
                ORDER BY id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                records.add(new ScenarioEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("pet_name"),
                        resultSet.getString("species"),
                        resultSet.getString("vaccine"),
                        resultSet.getDouble("fee")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database retrieval error: " + e.getMessage());
        }

        return records;
    }
}
