package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}
