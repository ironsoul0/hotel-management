package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeWorkingHoursRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/manager/")
public class EmployeeWorkingHoursController {

    @Autowired
    private EmployeeWorkingHoursRepository employeeWorkingHoursRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/allemployees")
    private List<Employee> showAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @GetMapping("/schedules")
    private List<EmployeeWorkingHours> showAllschedules() {
        return (List<EmployeeWorkingHours>) employeeWorkingHoursRepository.findAll();
    }

    @GetMapping("/schedule")
    private Set<EmployeeWorkingHours> showEmployeeschedules(@RequestParam Long employeeid ) {
        Employee employee = employeeRepository.findById(employeeid).orElseThrow();
        return employee.getEmployeeWorkingHours();
    }

    @GetMapping("/schedule/{id}/edit")
    public EmployeeWorkingHours scheduleEdit(@PathVariable(value = "id") long id) {
        EmployeeWorkingHours employeeWorkingHours = employeeWorkingHoursRepository.findById(id).orElseThrow();
        return employeeWorkingHours;
    }

    @PostMapping("/schedule/{id}/edit")
    public String schedulePostUpdate(@PathVariable long id, @RequestParam String datework,
                                     @RequestParam String time_start, @RequestParam String time_end,
                                     @RequestParam(required = false, defaultValue = "0") int total_payment) throws ParseException {
        EmployeeWorkingHours schedule = employeeWorkingHoursRepository.findById(id).orElseThrow();

        schedule.setTime_start(time_start);
        schedule.setTime_end(time_end);
        if (total_payment != 0) {
            schedule.setTotal_Payment(total_payment);
        }
        schedule.setDate_work(new SimpleDateFormat("yyyy-MM-dd").parse(datework));
        employeeWorkingHoursRepository.save(schedule);
        return "Changes done!";
    }

//    @GetMapping("/schedule/add")
//    public String hotelsAdd(Model model) {
//        return "hotels-add";
//    }

    @PostMapping("/schedule/add") // works
    public String schedulePostAdd(@RequestParam Long employeeId, @RequestParam String datework, @RequestParam String time_start, @RequestParam String time_end,
                                  @RequestParam(required = false, defaultValue = "0") int total_payment, @RequestParam int totalhours) throws ParseException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        EmployeeWorkingHours schedule;
        if (total_payment == 0)
            schedule = new EmployeeWorkingHours(employee, time_start, time_end, new SimpleDateFormat("yyyy-MM-dd").parse(datework), totalhours);
        else {
            schedule = new EmployeeWorkingHours(employee, time_start, time_end,
                    total_payment, new SimpleDateFormat("yyyy-MM-dd").parse(datework), totalhours);
        }
        employeeWorkingHoursRepository.save(schedule);
        return "Schedule registered successfully!";
    }

    @PostMapping("/schedule/{id}/delete")
    public String scheduleDelete(@PathVariable(value = "id") long id) {
        EmployeeWorkingHours schedule = employeeWorkingHoursRepository.findById(id).orElseThrow();
        employeeWorkingHoursRepository.delete(schedule);
        return "Schedule Deleted";
    }
}








