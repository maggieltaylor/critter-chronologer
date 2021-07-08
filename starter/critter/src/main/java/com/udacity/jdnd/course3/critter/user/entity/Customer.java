package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    @Nationalized
    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany
    private List<Pet> pets;
}
