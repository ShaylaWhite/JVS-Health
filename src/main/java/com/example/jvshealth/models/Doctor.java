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

    @OneToMany(mappedBy = "doctor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Patient> patientList;

    public Doctor(Long id, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;

    }

    public Doctor() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
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
