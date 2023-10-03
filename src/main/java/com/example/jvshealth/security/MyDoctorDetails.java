package com.example.jvshealth.security;

import com.example.jvshealth.models.Doctor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Custom UserDetails implementation for Doctor entities.
 */
public class MyDoctorDetails implements UserDetails {

    private final Doctor doctor;

    /**
     * Constructs a new MyDoctorDetails instance with the provided Doctor entity.
     *
     * @param doctor The Doctor entity for which to create UserDetails.
     */
    public MyDoctorDetails(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>(); // You can define authorities if needed.
    }

    @Override
    public String getPassword() {
        return doctor.getPassword();
    }

    @Override
    public String getUsername() {
        return doctor.getEmailAddress();
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

    /**
     * Get the Doctor entity associated with this UserDetails.
     *
     * @return The Doctor entity.
     */
    public Doctor getDoctor() {
        return doctor;
    }
}
