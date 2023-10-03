package com.example.jvshealth.repository;


import com.example.jvshealth.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Repository interface for managing Patient entities using Spring Data JPA.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Find a patient by the doctor's ID, patient's name, and patient's date of birth.
     *
     * @param doctorId    The ID of the doctor associated with the patient.
     * @param patientName The name of the patient.
     * @param patientDOB  The date of birth of the patient.
     * @return The patient matching the specified criteria or null if not found.
     */
    Patient findByDoctorIdAndNameAndBirthDate(Long doctorId, String patientName, LocalDate patientDOB);

    /**
     * Find patients by the doctor's ID.
     *
     * @param doctorId The ID of the doctor associated with the patients.
     * @return A list of patients associated with the specified doctor or an empty list if none found.
     */
    Patient findByDoctorId(Long doctorId);
}
