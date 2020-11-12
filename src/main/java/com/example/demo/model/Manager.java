package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager extends User{

    public Manager () { }

    public Manager (String username,
                    String email,
                    String name,
                    String surname,
                    String password,
                    String mobilePhone,
                    String homePhone,
                    String address,
                    String identificationType,
                    String identificationNumber)
    {

        super(username, email, name, surname, password, mobilePhone, homePhone, address, identificationType, identificationNumber);

    }
}
