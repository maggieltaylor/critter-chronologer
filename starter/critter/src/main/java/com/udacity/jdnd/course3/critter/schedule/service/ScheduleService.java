package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.repo.PetRepo;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repo.ScheduleRepo;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.repo.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final PetRepo petRepo;
    private final EmployeeRepo employeeRepo;
    private final CustomerRepo customerRepo;

    public Schedule createSchedule(Schedule schedule, List<Long> petIds, List<Long> employeeIds) {
        List<Pet> pets = petRepo.findAllById(petIds);
        schedule.setPets(pets);
        List<Employee> employees = employeeRepo.findAllById(employeeIds);
        schedule.setEmployees(employees);
        return scheduleRepo.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepo.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) throws PetNotFoundException {
        Pet pet = petRepo.findById(petId).orElseThrow(PetNotFoundException::new);
        return scheduleRepo.findByPets(pet);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        return scheduleRepo.findByEmployees(employee);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) throws CustomerNotFoundException {
        List<Pet> pets = customerRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new).getPets();
        return scheduleRepo.findByPetsIn(pets);
    }
}
