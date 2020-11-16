package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.EmployeeWorkingHoursRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/manager/")
public class EmployeeWorkingHoursController {

    @Autowired
    private EmployeeWorkingHoursRepository employeeWorkingHoursRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/allemployees")
    private List<User> showAllEmployees() {
        List<User> employees = new ArrayList<>();
        List<User> allUsers = (List<User>) userRepository.findAll();
        for (User user : allUsers) {
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getName().toString().toLowerCase().contains("mod")){
                    System.out.println(role.getName());
                    employees.add(user);
                    break;
                }
            }
        }
        return (List<User>) employees;
    }

    @GetMapping("/schedules")
    private List<EmployeeWorkingHours> showAllschedules() {
        return (List<EmployeeWorkingHours>) employeeWorkingHoursRepository.findAll();
    }

    @GetMapping("/schedule")
    private List<EmployeeWorkingHours> showEmployeeschedules(@RequestParam Long employeeid ) {
        List<EmployeeWorkingHours> allschedules = (List<EmployeeWorkingHours>) employeeWorkingHoursRepository.findAll();

        List<EmployeeWorkingHours> employeeschedules = new ArrayList<>();

        for (EmployeeWorkingHours schedule : allschedules) {
            if (schedule.getEmployeeId() == employeeid) {
                employeeschedules.add(schedule);
            }
        }
        return (List<EmployeeWorkingHours>) employeeschedules;
    }

//    @GetMapping("/schedule/{id}/edit")
//    public List<EmployeeWorkingHours> scheduleEdit(@PathVariable(value = "id") long id, Model model) {
//        if(!employeeWorkingHoursRepository.existsById(id)) {
//            return "redirect:/schedules";
//        }
//        Optional<EmployeeWorkingHours> schedule = EmployeeWorkingHoursRepository.findById(id);
//        ArrayList<EmployeeWorkingHours> res = new ArrayList<>();
//        schedule.ifPresent(res::add);
//        model.addAttribute("schedule", res);
//        return ;
//    }

//    @PostMapping("/schedule/{id}/edit")
//    public String schedulePostUpdate(@PathVariable(value = "employeeId") long employeeId, @RequestParam String weekday, @RequestParam Date datework, @RequestParam boolean is_extra, @RequestParam String time_start, @RequestParam String time_end, @RequestParam int payment, Model model) {
//        List<EmployeeWorkingHours> schedules = (List<EmployeeWorkingHours>) employeeWorkingHoursRepository.findAll();
//        EmployeeWorkingHours schedule = null;
//
//        for(EmployeeWorkingHours sc : schedules) {
//            if (employeeId == sc.getEmployeeId()) {
//                schedule = sc;
//            }
//        }
//
//        schedule.setTime_start(time_start);
//        schedule.setTime_end(time_end);
//        schedule.setPayment(payment);
//        schedule.setWeekday(weekday);
//        schedule.setIs_extra(is_extra);
//        schedule.setDate_work(datework);
//        EmployeeWorkingHoursRepository.save(schedule);
//        return "redirect:/schedules";
//    }

//    @GetMapping("/schedule/add")
//    public String hotelsAdd(Model model) {
//        return "hotels-add";
//    }

    @PostMapping("/add/schedule") // works
    public String schedulePostAdd(@RequestParam Long employeeId,  @RequestParam String weekday, @RequestParam String datework, @RequestParam boolean is_extra,
                                  @RequestParam String time_start, @RequestParam String time_end, @RequestParam int payment, @RequestParam int totalhours) throws ParseException {
        EmployeeWorkingHours schedule = new EmployeeWorkingHours(employeeId, weekday, time_start, time_end,
                payment, is_extra, new SimpleDateFormat("yyyy-MM-dd").parse(datework), totalhours);
        employeeWorkingHoursRepository.save(schedule);
        return "Schedule registered successfully!";
    }
}








