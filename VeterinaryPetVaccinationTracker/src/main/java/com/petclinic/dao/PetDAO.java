package com.petclinic.dao;

import com.petclinic.model.Pet;
import java.sql.SQLException;
import java.util.List;

public interface PetDAO {
    void add(Pet pet) throws SQLException;

    List<Pet> findAll() throws SQLException;
}
