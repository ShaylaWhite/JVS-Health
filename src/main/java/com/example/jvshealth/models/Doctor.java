package com.example.jvshealth.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String emailAddress;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "doctor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Prescription> prescriptionList;

    @OneToMany(mappedBy = "doctor", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Patient> patientList;

    // Constructors

    /**
     * Constructs a new Doctor object with the provided attributes.
     *
     * @param id           The unique identifier of the doctor.
     * @param firstName    The first name of the doctor.
     * @param lastName     The last name of the doctor.
     * @param emailAddress The email address of the doctor.
     */
    public Doctor(Long id, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    /**
     * Default constructor for the Doctor class.
     */
    public Doctor() {
    }

    // Getter and Setter methods

    /**
     * Get the unique identifier of the doctor.
     *
     * @return The unique identifier (ID) of the doctor.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the doctor.
     *
     * @param id The unique identifier (ID) to set for the doctor.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the first name of the doctor.
     *
     * @return The first name of the doctor.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the doctor.
     *
     * @param firstName The first name to set for the doctor.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the doctor.
     *
     * @return The last name of the doctor.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the doctor.
     *
     * @param lastName The last name to set for the doctor.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the email address of the doctor.
     *
     * @return The email address of the doctor.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set the email address of the doctor.
     *
     * @param emailAddress The email address to set for the doctor.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Get the list of prescriptions associated with the doctor.
     *
     * @return The list of prescriptions associated with the doctor.
     */
    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    /**
     * Set the list of prescriptions associated with the doctor.
     *
     * @param prescriptionList The list of prescriptions to set for the doctor.
     */
    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    /**
     * Get the list of patients associated with the doctor.
     *
     * @return The list of patients associated with the doctor.
     */
    public List<Patient> getPatientList() {
        return patientList;
    }

    /**
     * Set the list of patients associated with the doctor.
     *
     * @param patientList The list of patients to set for the doctor.
     */
    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    /**
     * Get the password of the doctor. (Note: The password is write-only and not exposed in responses.)
     *
     * @return The password of the doctor.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the doctor.
     *
     * @param password The password to set for the doctor.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString method

    /**
     * Generates a string representation of the Doctor object.
     *
     * @return A string containing the doctor's ID, first name, last name, and email address.
     */
    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}

