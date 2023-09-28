package com.example.jvshealth.service;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    DoctorRepository doctorRepository;
@Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor findUserByEmailAddress(String emailAddress){
        return doctorRepository.findUserByEmailAddress(emailAddress);
    }
}
