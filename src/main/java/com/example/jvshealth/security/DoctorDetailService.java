package com.example.jvshealth.security;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.service.DoctorService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DoctorDetailService implements UserDetailsService {

    private DoctorService doctorService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = DoctorService.findUserByEmailAddress;
        return new MyDoctorDetails(doctor);
    }
}
