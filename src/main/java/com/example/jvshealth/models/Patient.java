package com.example.jvshealth.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {

    private Long id;

    private String name;

    private LocalDate birthDate;
}
