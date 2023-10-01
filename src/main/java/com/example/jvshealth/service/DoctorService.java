package com.example.jvshealth.service;

import com.example.jvshealth.exception.InformationExistException;
import com.example.jvshealth.exception.InformationNotFoundException;
import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.models.Patient;
import com.example.jvshealth.models.Prescription;
import com.example.jvshealth.repository.DoctorRepository;
import com.example.jvshealth.repository.PatientRepository;
import com.example.jvshealth.repository.PrescriptionRepository;
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

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

//    public static Doctor findDoctorByEmailAddress;
    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PatientRepository patientRepository, PrescriptionRepository prescriptionRepository, @Lazy PasswordEncoder passwordEncoder,
                         JWTUtils jwtUtils,
                         @Lazy AuthenticationManager authenticationManager) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.prescriptionRepository = prescriptionRepository;
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
    

    //http://localhost:9092/api/doctors/1/patients/
    public Optional<Patient> createPatientDoctor(Long doctorId, Patient patientObject) {
        Patient patient = patientRepository.findByDoctorIdAndNameAndBirthDate(getCurrentLoggedInDoctor().getId(), patientObject.getName(), patientObject.getBirthDate());
        if (patient != null) {
            throw new InformationExistException("Patient already exists and is a patient of Doctor with id " + doctorId);
        } else {
            patientObject.setDoctor(getCurrentLoggedInDoctor());
            return Optional.of(patientRepository.save(patientObject));
        }
    }
    //http://localhost:9092/api/doctors/1/patients/
    public List<Patient> getAllPatients(Long doctorId) {
       Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
       if(doctorOptional.isPresent()) {
           List<Patient> patientList = doctorOptional.get().getPatientList();
           return patientList;
       } else {
           throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
       }
    }

    public Optional<Patient> getPatientById(Long doctorId, Long patientId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if(doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = patientRepository.findById(patientId);
            return patientOptional;
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    public Optional<Patient> updatePatientById(Long doctorId, Long patientId, Patient patientObject) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if(doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = patientRepository.findById(patientId);
            patientOptional.get().setName(patientObject.getName());
            patientOptional.get().setBirthDate(patientObject.getBirthDate());
patientRepository.save(patientOptional.get());
            return patientOptional;
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    public Optional<Patient> deletePatientById(Long doctorId, Long patientId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if(doctorOptional.isPresent()) {
         Optional<Patient> patientOptional = patientRepository.findById(patientId);
            patientRepository.deleteById(patientId);
            return patientOptional;
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    public Optional<Prescription> createPrescriptionPatient(Long doctorId, Long patientId, Prescription prescriptionObject) {
        Optional<Patient> patientOptional = Optional.ofNullable(patientRepository.findByDoctorId(doctorId));

        if (patientOptional.isEmpty()) {
            throw new InformationNotFoundException("Patient with id " + patientId + " does not exist");
        } else {
           Optional<Prescription> prescriptionOptional =
                   prescriptionRepository.findByPatientIdAndDetails(patientId, prescriptionObject.getDetails());
if(prescriptionOptional.isEmpty()) {
    prescriptionObject.setDoctor(DoctorService.getCurrentLoggedInDoctor());
    prescriptionObject.setPatient(patientOptional.get());
   return Optional.of(prescriptionRepository.save(prescriptionObject));

}else {
return Optional.empty();
}
        }

    }
}
