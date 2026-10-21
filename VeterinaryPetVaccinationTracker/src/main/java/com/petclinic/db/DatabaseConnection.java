package com.petclinic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:pet_vaccination.db";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() throws SQLException {
        String createTable = """
                CREATE TABLE IF NOT EXISTS pet_vaccinations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    pet_name TEXT NOT NULL,
                    species TEXT NOT NULL CHECK (species IN ('DOG', 'CAT', 'BIRD')),
                    vaccine_type TEXT NOT NULL CHECK (vaccine_type IN ('RABIES', 'DHPP', 'FVRCP')),
                    fee REAL NOT NULL CHECK (fee >= 0)
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTable);
        }
    }
}
