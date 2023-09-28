package com.example.jvshealth.security;

import com.example.jvshealth.models.Doctor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class MyDoctorDetails implements UserDetails {

    private final Doctor doctor;

    public MyDoctorDetails(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  new HashSet<>();
    }

    @Override
    public String getPassword() {
        return doctor.getPassword();
    }

    @Override
    public String getUsername() {
        return doctor.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Doctor getDoctor() {
        return doctor;
    }
}
