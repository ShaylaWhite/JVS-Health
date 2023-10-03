package com.example.jvshealth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @Column
    private String details;

    /**
     * Default constructor for the Prescription class.
     */
    public Prescription() {
    }

    /**
     * Constructs a new Prescription object with the provided attributes.
     *
     * @param id      The unique identifier of the prescription.
     * @param doctor  The doctor who issued the prescription.
     * @param patient The patient for whom the prescription is issued.
     * @param details The details of the prescription.
     */
    public Prescription(Long id, Doctor doctor, Patient patient, String details) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.details = details;
    }

    /**
     * Get the unique identifier of the prescription.
     *
     * @return The unique identifier (ID) of the prescription.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the prescription.
     *
     * @param id The unique identifier (ID) to set for the prescription.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the doctor who issued the prescription.
     *
     * @return The doctor who issued the prescription.
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Set the doctor who issued the prescription.
     *
     * @param doctor The doctor to set as the issuing physician for the prescription.
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Get the patient for whom the prescription is issued.
     *
     * @return The patient for whom the prescription is issued.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Set the patient for whom the prescription is issued.
     *
     * @param patient The patient to set as the recipient of the prescription.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Get the details of the prescription.
     *
     * @return The details of the prescription.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the details of the prescription.
     *
     * @param details The details to set for the prescription.
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Generates a string representation of the Prescription object.
     *
     * @return A string containing the prescription's ID, doctor, patient, and details.
     */
    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", details='" + details + '\'' +
                '}';
    }
}
