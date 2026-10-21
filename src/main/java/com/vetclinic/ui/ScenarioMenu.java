package com.vetclinic.ui;

import com.vetclinic.dao.ScenarioDao;
import com.vetclinic.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ScenarioDao scenarioDao = new ScenarioDao();

    public void start() {
        boolean running = true;

        while (running) {
            printHeader();
            System.out.println("[1] Add Pet Record");
            System.out.println("[2] View Vaccination Records");
            System.out.println("[3] Exit System");
            System.out.println("==================================================");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> addPetRecord();
                case 2 -> viewRecords();
                case 3 -> {
                    System.out.println();
                    System.out.println("Exiting system... Thank you!");
                    running = false;
                }
                default -> {
                    System.out.println();
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    System.out.println();
                }
            }
        }
    }

    private void addPetRecord() {
        System.out.println();
        System.out.println("--- ADD PET VACCINATION RECORD ---");

        String petName = readNonBlank("Enter Pet Name: ");
        String species = readAllowedValue(
                "Enter Species (DOG / CAT / BIRD): ",
                new String[]{"DOG", "CAT", "BIRD"}
        );
        String vaccine = readAllowedValue(
                "Enter Vaccine Type (RABIES / DHPP / FVRCP): ",
                new String[]{"RABIES", "DHPP", "FVRCP"}
        );
        double fee = readNonNegativeDouble("Enter Service Fee: ");

        ScenarioEntity pet = new ScenarioEntity(0, petName, species, vaccine, fee);

        if (scenarioDao.addRecord(pet)) {
            System.out.println();
            System.out.println("SUCCESS: Pet vaccination record saved!");
        } else {
            System.out.println();
            System.out.println("ERROR: Pet vaccination record could not be saved.");
        }
        System.out.println();
    }

    private void viewRecords() {
        List<ScenarioEntity> records = scenarioDao.getAllRecords();

        System.out.println();
        System.out.println("================================================================================");
        System.out.printf("%45s%n", "PET VACCINATION RECORDS");
        System.out.println("================================================================================");
        System.out.println("+----+--------------------+----------+----------+------------+");
        System.out.printf("| %-2s | %-18s | %-8s | %-8s | %-10s |%n",
                "ID", "PET NAME", "SPECIES", "VACCINE", "FEE");
        System.out.println("+----+--------------------+----------+----------+------------+");

        for (ScenarioEntity pet : records) {
            System.out.printf("| %-2d | %-18s | %-8s | %-8s | %10.2f |%n",
                    pet.id(),
                    shorten(pet.petName(), 18),
                    pet.species(),
                    pet.vaccine(),
                    pet.fee());
        }

        System.out.println("+----+--------------------+----------+----------+------------+");
        System.out.println("Total Records Displayed: " + records.size());
        System.out.println();
    }

    private void printHeader() {
        System.out.println();
        System.out.println("==================================================");
        System.out.printf("            PET CLINIC VACCINATION LOG");
        System.out.println("\n==================================================");
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Enter a menu choice: ");
            }
        }
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be blank. Please try again.");
        }
    }

    private String readAllowedValue(String prompt, String[] allowedValues) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().toUpperCase();

            for (String allowed : allowedValues) {
                if (allowed.equals(value)) {
                    return value;
                }
            }

            System.out.print("Invalid value. Allowed values: ");
            System.out.println(String.join(" / ", allowedValues));
        }
    }

    private double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double fee = Double.parseDouble(input);
                if (Double.isFinite(fee) && fee >= 0) {
                    return fee;
                }
            } catch (NumberFormatException ignored) {
                // Prompt again below.
            }

            System.out.println("Please enter a valid fee of 0 or more (example: 500.00).");
        }
    }

    private String shorten(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }
}
