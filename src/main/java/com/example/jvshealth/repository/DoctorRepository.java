package com.example.jvshealth.repository;

import com.example.jvshealth.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    Doctor findDoctorByEmailAddress(String emailAddress);
}
