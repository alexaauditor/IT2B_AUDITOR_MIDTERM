package com.vetclinic.model;

public record ScenarioEntity(
        int id,
        String petName,
        String species,
        String vaccine,
        double fee
) {
}
