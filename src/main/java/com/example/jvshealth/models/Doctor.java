package com.example.jvshealth.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor {

    private Long id;

    private String firstName;

    private String lastName;

}
