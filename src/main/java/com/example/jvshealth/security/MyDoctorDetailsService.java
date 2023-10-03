package com.example.jvshealth.security;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom UserDetailsService implementation for loading Doctor user details.
 */
@Service
public class MyDoctorDetailsService implements UserDetailsService {

    private DoctorService doctorService;

    /**
     * Set the DoctorService for this UserDetailsService.
     *
     * @param doctorService The DoctorService to set.
     */
    @Autowired
    public void setDoctorDetailsService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Load user details by the provided email address.
     *
     * @param emailAddress The email address of the user to load.
     * @return UserDetails for the user with the specified email address.
     * @throws UsernameNotFoundException If the user with the given email address is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Doctor doctor = doctorService.findDoctorByEmailAddress(emailAddress);
        if (doctor == null) {
            throw new UsernameNotFoundException("Doctor not found with email: " + emailAddress);
        }
        return new MyDoctorDetails(doctor);
    }
}
