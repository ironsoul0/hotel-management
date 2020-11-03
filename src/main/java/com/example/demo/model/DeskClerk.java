package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="deskclerk")
public class DeskClerk extends User {

    public DeskClerk () { }

    public DeskClerk(String username, String email, String name, String surname, String password, String mobilePhone, String homePhone, String address, String identificationType, String identificationNumber) {

        super(username, email, name, surname, password, mobilePhone, homePhone, address, identificationType, identificationNumber);

        Set <Role> roles = new HashSet<>();

        roles.add(new Role(ERole.ROLE_USER));
        roles.add(new Role(ERole.ROLE_MODERATOR));

        setRoles(roles);
    }
}