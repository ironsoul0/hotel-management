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
    private int payment;
    private int total_hours;
    private Date date_work; // if is_extra true, we don't need this

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee employee;

    public EmployeeWorkingHours () {

    }


    public EmployeeWorkingHours (Employee employee, String weekday, String time_start, String time_end, int payment, boolean is_extra,
                                Date date_work, int total_hours) {
        this.employee = employee;
        this.time_start = time_start;
        this.time_end = time_end;
        this.payment = payment;
        this.date_work = date_work;
        this.total_hours = total_hours;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime_start(String time_start) {
        return this.time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end(String time_end) {
        return this.time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
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

}
