package com.petclinic.model;

public class Pet {
    private final int id;
    private final String petName;
    private final String species;
    private final String vaccineType;
    private final double fee;

    public Pet(int id, String petName, String species, String vaccineType, double fee) {
        this.id = id;
        this.petName = petName;
        this.species = species;
        this.vaccineType = vaccineType;
        this.fee = fee;
    }

    public Pet(String petName, String species, String vaccineType, double fee) {
        this(0, petName, species, vaccineType, fee);
    }

    public int getId() {
        return id;
    }

    public String getPetName() {
        return petName;
    }

    public String getSpecies() {
        return species;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public double getFee() {
        return fee;
    }
}
