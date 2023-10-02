package com.example.jvshealth.repository;

import com.example.jvshealth.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByDoctorIdAndNameAndBirthDate(Long doctorId, String patientName, LocalDate patientDOB);

    Patient findByDoctorId(Long doctorId);
}