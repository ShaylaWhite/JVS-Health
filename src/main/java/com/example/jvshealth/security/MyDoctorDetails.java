package com.example.jvshealth.security;

import com.example.jvshealth.models.Doctor;
import org.springframework.security.core.userdetails.UserDetails;

public class MyDoctorDetails implements UserDetails {

    private final Doctor doctor;

}
