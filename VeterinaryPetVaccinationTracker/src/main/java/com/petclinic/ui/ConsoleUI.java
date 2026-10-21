
package com.petclinic.ui;

import com.petclinic.model.Pet;
import com.petclinic.service.PetService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements AutoCloseable {

    private static final String LINE = "==============================================";

    private final PetService petService;
    private final Scanner scanner;

    public ConsoleUI(PetService petService) {
        this.petService = petService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = readLine("Enter choice: ").trim();

            switch (choice) {
                case "1" -> addPetRecord();
                case "2" -> viewRecords();
                case "3" -> {
                    System.out.println();
                    System.out.println("Exiting system... Thank you!");
                    running = false;
                }
                default -> {
                    System.out.println();
                    System.out.println("INVALID CHOICE: Please enter 1, 2, or 3.");
                    System.out.println();
                }
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println(LINE);
        System.out.println("          PET CLINIC VACCINATION LOG");
        System.out.println(LINE);
        System.out.println("[1] Add Pet Record");
        System.out.println("[2] View Vaccination Records");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
    }

    private void addPetRecord() {
        System.out.println();
        System.out.println("--- ADD PET VACCINATION RECORD ---");
        System.out.println();

        String petName = readLine("Enter Pet Name: ");
        String species = readLine("Enter Species (DOG / CAT / BIRD): ");
        String vaccineType = readLine("Enter Vaccine Type (RABIES / DHPP / FVRCP): ");
        double fee = readFee();

        try {
            petService.registerPet(petName, species, vaccineType, fee);

            System.out.println();
            System.out.println("SUCCESS: Pet vaccination record saved!");

        } catch (IllegalArgumentException exception) {
            System.out.println();
            System.out.println("INPUT ERROR: " + exception.getMessage());
            System.out.println("Record was not saved. Please try again.");

        } catch (SQLException exception) {
            System.out.println();
            System.out.println("DATABASE ERROR: Unable to save the record.");
            System.out.println("Details: " + exception.getMessage());
        }

        System.out.println();
    }

    private double readFee() {
        while (true) {
            String input = readLine("Enter Service Fee: ").trim();

            try {
                double fee = Double.parseDouble(input);

                if (!Double.isFinite(fee) || fee < 0) {
                    System.out.println(
                            "Invalid fee. Enter a number greater than or equal to 0."
                    );
                    continue;
                }

                return fee;

            } catch (NumberFormatException exception) {
                System.out.println(
                        "Invalid fee. Please enter a numeric amount, such as 500.00."
                );
            }
        }
    }

    private void viewRecords() {
        try {
            List<Pet> pets = petService.getAllPets();

            System.out.println();
            System.out.println("======================================================");
            System.out.println("               PET VACCINATION RECORDS");
            System.out.println("======================================================");

            printBorder();
            printRow("ID", "PET NAME", "SPECIES", "VACCINE", "FEE");
            printBorder();

            for (Pet pet : pets) {
                printRow(
                        String.valueOf(pet.getId()),
                        pet.getPetName(),
                        pet.getSpecies(),
                        pet.getVaccineType(),
                        String.format(
                                java.util.Locale.US,
                                "%.2f",
                                pet.getFee()
                        )
                );
            }

            printBorder();

            System.out.println(
                    "Total Records Displayed: " + pets.size()
            );

        } catch (SQLException exception) {
            System.out.println();
            System.out.println("DATABASE ERROR: Unable to retrieve records.");
            System.out.println("Details: " + exception.getMessage());
        }

        System.out.println();
    }

    private void printBorder() {
        System.out.println(
                "+----+------------+----------+----------+----------+"
        );
    }

    private void printRow(
            String id,
            String name,
            String species,
            String vaccine,
            String fee
    ) {
        System.out.printf(
                "| %-2s | %-10s | %-8s | %-8s | %-8s |%n",
                fit(id, 2),
                fit(name, 10),
                fit(species, 8),
                fit(vaccine, 8),
                fit(fee, 8)
        );
    }

    private String fit(String value, int width) {
        if (value == null) {
            value = "";
        }

        if (value.length() > width) {
            return value.substring(0, width);
        }

        return value;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
