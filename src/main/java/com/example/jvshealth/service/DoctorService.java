package com.example.jvshealth.service;

import com.example.jvshealth.exception.InformationExistException;
import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.models.Patient;
import com.example.jvshealth.repository.DoctorRepository;
import com.example.jvshealth.repository.PatientRepository;
import com.example.jvshealth.request.LoginRequest;
import com.example.jvshealth.security.JWTUtils;
import com.example.jvshealth.security.MyDoctorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

//    public static Doctor findDoctorByEmailAddress;
    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PatientRepository patientRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public static Doctor getCurrentLoggedInDoctor() {
        MyDoctorDetails doctorDetails = (MyDoctorDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return doctorDetails.getDoctor();
    }

    public Doctor createDoctor(Doctor doctorObject) {
        if (!doctorRepository.existsByEmailAddress(doctorObject.getEmailAddress())) {
            doctorObject.setPassword(passwordEncoder.encode(doctorObject.getPassword()));
            return doctorRepository.save(doctorObject);
        } else {
            throw new InformationExistException("user with email address " + doctorObject.getEmailAddress() + " already exists");
        }
    }
    public Optional<String> loginDoctor(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyDoctorDetails myDoctorDetails = (MyDoctorDetails) authentication.getPrincipal();
            return Optional.of(jwtUtils.generateJwtToken(myDoctorDetails));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Doctor findDoctorByEmailAddress(String emailAddress){
        return doctorRepository.findDoctorByEmailAddress(emailAddress);
    }

    public Optional<Patient> createPatientDoctor(Long doctorId, Patient patientObject) {
        try {
            Patient patient = patientRepository.findByDoctorId(getCurrentLoggedInDoctor().getId(), patientObject.getName(), patientObject.getBirthDate());
            if (patient != null) {
                throw new InformationExistException("Patient already exists and is a patient of Doctor with id " + doctorId);
            } else {
                patient.setDoctor(getCurrentLoggedInDoctor());
                return Optional.of(patientRepository.save(patientObject));
            }
        }
    }
}
