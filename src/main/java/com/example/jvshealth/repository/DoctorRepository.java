package com.example.jvshealth.repository;

import com.example.jvshealth.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Doctor entities using Spring Data JPA.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Find a doctor by their email address.
     *
     * @param emailAddress The email address of the doctor to find.
     * @return The doctor with the specified email address or null if not found.
     */
    Doctor findDoctorByEmailAddress(String emailAddress);

    /**
     * Check if a doctor with a given email address exists.
     *
     * @param emailAddress The email address to check for existence.
     * @return True if a doctor with the email address exists, false otherwise.
     */
    boolean existsByEmailAddress(String emailAddress);
}
