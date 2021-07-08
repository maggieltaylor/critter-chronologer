package com.udacity.jdnd.course3.critter.pet.entity;

import com.udacity.jdnd.course3.critter.pet.enumeration.PetType;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue
    private long id;

    private PetType type;

    @Nationalized
    private String name;

    private LocalDate birthDate;

    private String notes;

    @ManyToOne
    private Customer customer;
}
