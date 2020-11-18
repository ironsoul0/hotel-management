package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "employee",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username")
        })
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;

    private String username, email, name, surname, password, phoneNumber;
    private String role;
    private int payment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id_employee")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonBackReference
    private Hotel hotel_id_employee;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeWorkingHours> employeeWorkingHours = new HashSet<>();

    public Employee() {

    }

    public Employee(String username, String email, String name, String surname, String password, String phoneNumber, int payment, String role, Hotel hotel) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.payment = payment;
        this.role = role;
        this.hotel_id_employee = hotel;
    }

    public Long getId() {
        return employee_id;
    }

    public void setId(Long id) {
        this.employee_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<EmployeeWorkingHours> getEmployeeWorkingHours() {
        return employeeWorkingHours;
    }

    public void setEmployeeWorkingHours(Set<EmployeeWorkingHours> employeeWorkingHours) {
        this.employeeWorkingHours = employeeWorkingHours;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHotelName() {
        return this.hotel_id_employee.getName();
    }

    public String getHotelAddress() {
        return this.hotel_id_employee.getAddress();
    }

}
