package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.repo.PetRepo;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {
    private final PetRepo petRepo;
    private final CustomerRepo customerRepo;

    public Pet savePet(Pet pet, Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElse(new Customer());
        pet.setCustomer(customer);
        Pet savedPet = petRepo.save(pet);
        List<Pet> customerPets = customer.getPets();
        customerPets.add(pet);
        customer.setPets(customerPets);
        customerRepo.save(customer);
        return savedPet;
    }

    public Pet getPet(long petId) throws PetNotFoundException {
        return petRepo.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> getAllPets() {
        return petRepo.findAll();
    }

    public List<Pet> getPetByOwnerId(long ownerId) {
        Customer customer = customerRepo.findById(ownerId).orElse(new Customer());
        return customer.getPets();
    }
}
