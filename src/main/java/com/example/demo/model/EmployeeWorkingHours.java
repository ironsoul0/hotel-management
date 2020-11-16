package com.example.demo.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee_working_hours")
public class EmployeeWorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long employeeId;


    private String weekday; // if is_extra false, we don't need this
    private String time_start;
    private String time_end;
    private int payment;
    private boolean is_extra;
    private int total_hours;
    private Date date_work; // if is_extra true, we don't need this

    public EmployeeWorkingHours () {

    }


    public EmployeeWorkingHours (Long employeeId, String weekday, String time_start, String time_end, int payment, boolean is_extra,
                                Date date_work, int total_hours) {
        this.employeeId = employeeId;
        this.weekday = weekday;
        this.time_start = time_start;
        this.time_end = time_end;
        this.payment = payment;
        this.is_extra = is_extra;
        this.date_work = date_work;
        this.total_hours = total_hours;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
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

    public boolean isIs_extra() {
        return is_extra;
    }

    public void setIs_extra(boolean is_extra) {
        this.is_extra = is_extra;
    }

    public Date getDate_work() {
        return date_work;
    }

    public void setDate_work(Date date_work) {
        this.date_work = date_work;
    }

}
