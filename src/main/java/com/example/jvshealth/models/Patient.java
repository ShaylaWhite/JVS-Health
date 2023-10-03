package com.example.jvshealth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate birthDate;

    @OneToMany(mappedBy = "patient")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Prescription> prescriptionList;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Doctor doctor;

    /**
     * Default constructor for the Patient class.
     */
    public Patient() {
    }

    /**
     * Constructs a new Patient object with the provided attributes.
     *
     * @param id        The unique identifier of the patient.
     * @param name      The name of the patient.
     * @param birthDate The birth date of the patient.
     */
    public Patient(Long id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    /**
     * Get the unique identifier of the patient.
     *
     * @return The unique identifier (ID) of the patient.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the patient.
     *
     * @param id The unique identifier (ID) to set for the patient.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the patient.
     *
     * @return The name of the patient.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the patient.
     *
     * @param name The name to set for the patient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the birth date of the patient.
     *
     * @return The birth date of the patient.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Set the birth date of the patient.
     *
     * @param birthDate The birth date to set for the patient.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Get the list of prescriptions associated with the patient.
     *
     * @return The list of prescriptions associated with the patient.
     */
    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    /**
     * Set the list of prescriptions associated with the patient.
     *
     * @param prescriptionList The list of prescriptions to set for the patient.
     */
    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    /**
     * Get the doctor associated with the patient.
     *
     * @return The doctor associated with the patient.
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Set the doctor associated with the patient.
     *
     * @param doctor The doctor to set as the attending physician for the patient.
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Generates a string representation of the Patient object.
     *
     * @return A string containing the patient's ID, name, and birth date.
     */
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +

