package com.example.demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Size(max = 120)
    private String globalCategory;

    @Size(max = 120)
    private String localCategory;

    @NotBlank
    @Size(max = 120)
    private String mobilePhone;

    @NotBlank
    @Size(max = 120)
    private String homePhone;

    @NotBlank
    @Size(max = 120)
    private String address;

    @NotBlank
    @Size(max = 120)
    private String identificationType;

    @NotBlank
    @Size(max = 120)
    private String identificationNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id_role"),
            inverseJoinColumns = @JoinColumn(name = "role_id_user"))
    private Set<Role> roles = new HashSet<>();

    /* @OneToMany(mappedBy = "user_id_reservation", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>(); */

    public User() {
    }

    public User(String username, String email, String password, String mobilePhone, String homePhone, String address, String identificationType, String identificationNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.identificationType = identificationType;
        this.identificationNumber = identificationNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}