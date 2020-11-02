package com.example.demo.model;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;


@Entity
public class DeskClerk extends User {

    public DeskClerk () { }

    public DeskClerk(String username, String email, String password, String mobilePhone, String homePhone, String address, String identificationType, String identificationNumber) {

        super(username, email, password, mobilePhone, homePhone, address, identificationType, identificationNumber);

        Set <Role> roles = new HashSet<>();

        roles.add(new Role(ERole.ROLE_USER));
        roles.add(new Role(ERole.ROLE_MODERATOR));

        setRoles(roles);
    }
}