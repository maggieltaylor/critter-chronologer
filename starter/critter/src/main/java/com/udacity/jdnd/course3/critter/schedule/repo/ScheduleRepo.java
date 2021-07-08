package com.udacity.jdnd.course3.critter.schedule.repo;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPets(Pet pet);

    List<Schedule> findByEmployees(Employee employee);

    List<Schedule> findByPetsIn(List<Pet> pets);
}
