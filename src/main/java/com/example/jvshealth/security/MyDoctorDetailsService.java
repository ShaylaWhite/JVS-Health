package com.example.jvshealth.security;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyDoctorDetailsService implements UserDetailsService {

    private DoctorService doctorService;
    @Autowired
    public void setDoctorDetailsService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Doctor doctor = doctorService.findDoctorByEmailAddress(emailAddress);
        return new MyDoctorDetails(doctor);
    }
}
