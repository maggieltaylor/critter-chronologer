package com.udacity.jdnd.course3.critter.user.service;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.repo.PetRepo;
import com.udacity.jdnd.course3.critter.user.enumeration.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repo.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final CustomerRepo customerRepo;
    private final EmployeeRepo employeeRepo;
    private final PetRepo petRepo;

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Long> petIds2 = Optional.ofNullable(petIds).orElse(Collections.emptyList());
        List<Pet> pets = petIds2.stream().map(id -> petRepo.findById(id).orElse(null)).collect(Collectors.toList());
        customer.setPets(pets);
        return customerRepo.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee getEmployee(long employeeId) throws EmployeeNotFoundException {
        return employeeRepo.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public Customer getOwnerByPetId(long petId) throws PetNotFoundException, CustomerNotFoundException {
        Pet pet = petRepo.findById(petId).orElseThrow(PetNotFoundException::new);
        return customerRepo.findById(pet.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepo.save(employee);
    }

    public List<Employee> getAvailableEmployees(LocalDate date, Set<EmployeeSkill> employeeSkills) {
        List<Employee> employees = employeeRepo.findByDaysAvailableIn(Sets.newHashSet(date.getDayOfWeek()));
        return employees.stream().filter(employee -> employee.getSkills().containsAll(employeeSkills)).collect(Collectors.toList());
    }
}
