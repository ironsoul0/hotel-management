package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeWorkingHours;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeWorkingHoursRepository extends CrudRepository<EmployeeWorkingHours, Long> {

}
