package com.petclinic;

import com.petclinic.dao.PetDAO;
import com.petclinic.dao.PetDAOImpl;
import com.petclinic.db.DatabaseConnection;
import com.petclinic.service.PetService;
import com.petclinic.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();

            PetDAO petDAO = new PetDAOImpl();
            PetService petService = new PetService(petDAO);

            try (ConsoleUI consoleUI = new ConsoleUI(petService)) {
                consoleUI.run();
            }
        } catch (Exception exception) {
            System.err.println("APPLICATION ERROR: " + exception.getMessage());
            System.err.println("Check that the application can create/write the database file.");
        }
    }
}
