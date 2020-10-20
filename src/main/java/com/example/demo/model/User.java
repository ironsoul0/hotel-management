package com.example.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private Set <Role> roles;

    public Long getID () { return id; }

    public void setID (Long id) { this.id = id; }

    public String getUserName () { return userName; }

    public void setUserName (String userName) { this.userName = userName; }

    public String getPassword () { return password; }

    public void setPassword (String password) { this.password = password; }

    public Set<Role> getRoles () { return roles; }

    public void setRoles (Set <Role> roles) { this.roles = roles; }

    public String getPasswordConfirm () { return passwordConfirm; }

    public void setPasswordConfirm (String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
}
