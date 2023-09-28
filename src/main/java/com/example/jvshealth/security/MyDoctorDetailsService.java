package com.example.jvshealth.security;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.service.DoctorService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyDoctorDetailsService implements UserDetailsService {

    private DoctorService doctorService;

    public MyDoctorDetailsService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Doctor doctor = DoctorService.findDoctorByEmailAddress;
        return new MyDoctorDetails(doctor);
    }
}
