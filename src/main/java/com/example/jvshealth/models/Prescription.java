package com.example.jvshealth.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    private Long id;

    private Doctor doctor;

    private Patient patient;

    private String details;
}
