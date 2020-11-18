package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee_working_hours")
public class EmployeeWorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String time_start;
    private String time_end;
    private int total_payment;
    private int total_hours;
    private Date date_work;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee employee;

    public EmployeeWorkingHours () {

    }

    public EmployeeWorkingHours (Employee employee, String time_start, String time_end,
                                 Date date_work, int total_hours) {
        this.employee = employee;
        this.time_start = time_start;
        this.time_end = time_end;
        this.total_payment = employee.getPayment() * total_hours;
        this.date_work = date_work;
        this.total_hours = total_hours;
    }


    public EmployeeWorkingHours (Employee employee, String time_start, String time_end, int total_payment,
                                Date date_work, int total_hours) {
        this.employee = employee;
        this.time_start = time_start;
        this.time_end = time_end;
        this.total_payment = total_payment;
        this.date_work = date_work;
        this.total_hours = total_hours;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime_start() {
        return this.time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return this.time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public int getTotal_Payment() {
        return total_payment;
    }

    public void setTotal_Payment(int total_payment) {
        this.total_payment = total_payment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate_work() {
        return date_work;
    }

    public void setDate_work(Date date_work) {
        this.date_work = date_work;
    }

    public int getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(int total_hours) {
        this.total_hours = total_hours;
    }
}
