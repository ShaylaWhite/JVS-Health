package com.example.jvshealth.repository;

import com.example.jvshealth.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Prescription entities using Spring Data JPA.
 */
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    /**
     * Find a prescription by the patient's ID and prescription details.
     *
     * @param patientId The ID of the patient associated with the prescription.
     * @param details   The details of the prescription.
     * @return An Optional containing the prescription matching the specified criteria or an empty Optional if not found.
     */
    Optional<Prescription> findByPatientIdAndDetails(Long patientId, String details);

    /**
     * Find prescriptions by the patient's ID.
     *
     * @param patientId The ID of the patient associated with the prescriptions.
     * @return A list of prescriptions associated with the specified patient or an empty list if none found.
     */
    Optional<Prescription> findByPatientId(Long patientId);

    /**
     * Find a prescription by the patient's ID and prescription ID.
     *
     * @param patientId The ID of the patient associated with the prescription.
     * @param id        The ID of the prescription.
     * @return An Optional containing the prescription matching the specified criteria or an empty Optional if not found.
     */
    Optional<Prescription> findByPatientIdAndId(Long patientId, Long id);
}
