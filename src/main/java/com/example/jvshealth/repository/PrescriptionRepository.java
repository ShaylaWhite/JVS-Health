package com.example.jvshealth.repository;

import com.example.jvshealth.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByPatientIdAndDetails(Long patientId, String details);

    Optional<Prescription> findByPatientId(Long patientId);

    Optional<Prescription> findByPatientIdandId(Long patientId);
}
