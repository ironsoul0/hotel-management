package com.example.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany (mappedBy = "roles")
    private Set<User> users;

    public Long getID () { return id; }

    public void setID (Long id) { this.id = id; }

    public String getName () { return name; }

    public void setName (String name) { this.name = name; }

    public Set <User> getUsers () { return users; }

    public void setUsers (Set <User> users) { this.users = users; }
}
