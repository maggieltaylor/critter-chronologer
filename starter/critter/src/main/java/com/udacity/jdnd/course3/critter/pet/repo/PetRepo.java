package com.udacity.jdnd.course3.critter.pet.repo;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepo extends JpaRepository<Pet, Long> {
}
