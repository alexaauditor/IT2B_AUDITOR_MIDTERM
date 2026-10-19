package com.petclinic.dao;

import com.petclinic.db.DatabaseConnection;
import com.petclinic.model.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {

    @Override
    public void add(Pet pet) throws SQLException {
        String sql = """
                INSERT INTO pet_vaccinations (pet_name, species, vaccine_type, fee)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pet.getPetName());
            statement.setString(2, pet.getSpecies());
            statement.setString(3, pet.getVaccineType());
            statement.setDouble(4, pet.getFee());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Pet> findAll() throws SQLException {
        String sql = """
                SELECT id, pet_name, species, vaccine_type, fee
                FROM pet_vaccinations
                ORDER BY id ASC
                """;

        List<Pet> pets = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pets.add(new Pet(
                        resultSet.getInt("id"),
                        resultSet.getString("pet_name"),
                        resultSet.getString("species"),
                        resultSet.getString("vaccine_type"),
                        resultSet.getDouble("fee")
                ));
            }
        }

        return pets;
    }
}
