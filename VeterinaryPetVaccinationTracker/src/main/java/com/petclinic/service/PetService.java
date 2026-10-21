package com.petclinic.service;

import com.petclinic.dao.PetDAO;
import com.petclinic.model.Pet;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PetService {
    private static final Set<String> VALID_SPECIES = Set.of("DOG", "CAT", "BIRD");
    private static final Set<String> VALID_VACCINES = Set.of("RABIES", "DHPP", "FVRCP");

    private final PetDAO petDAO;

    public PetService(PetDAO petDAO) {
        this.petDAO = petDAO;
    }

    public void registerPet(String petName, String species, String vaccineType, double fee)
            throws SQLException {
        String cleanedName = petName == null ? "" : petName.trim();
        String cleanedSpecies = species == null ? "" : species.trim().toUpperCase(Locale.ROOT);
        String cleanedVaccine = vaccineType == null ? "" : vaccineType.trim().toUpperCase(Locale.ROOT);

        if (cleanedName.isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be empty.");
        }
        if (!VALID_SPECIES.contains(cleanedSpecies)) {
            throw new IllegalArgumentException("Species must be DOG, CAT, or BIRD.");
        }
        if (!VALID_VACCINES.contains(cleanedVaccine)) {
            throw new IllegalArgumentException("Vaccine type must be RABIES, DHPP, or FVRCP.");
        }
        if (!Double.isFinite(fee) || fee < 0) {
            throw new IllegalArgumentException("Service fee must be a valid number greater than or equal to 0.");
        }

        petDAO.add(new Pet(cleanedName, cleanedSpecies, cleanedVaccine, fee));
    }

    public List<Pet> getAllPets() throws SQLException {
        return petDAO.findAll();
    }
}
