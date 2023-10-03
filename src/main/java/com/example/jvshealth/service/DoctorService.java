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
/**
 * Service class for managing doctors, patients, and prescriptions.
 */

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
    /**
     * Create a new doctor if the email address is unique.
     *
     * @param doctorObject The doctor object to create.
     * @return The created doctor.
     * @throws InformationExistException If a doctor with the same email address already exists.
     */
    public Doctor createDoctor(Doctor doctorObject) {
        if (!doctorRepository.existsByEmailAddress(doctorObject.getEmailAddress())) {
            doctorObject.setPassword(passwordEncoder.encode(doctorObject.getPassword()));
            return doctorRepository.save(doctorObject);
        } else {
            throw new InformationExistException("user with email address " + doctorObject.getEmailAddress() + " already exists");
        }
    }


    /**
     * Authenticate a doctor using their email address and password and generate a JWT token upon successful authentication.
     *
     * @param loginRequest The login request containing email and password.
     * @return Optional containing the JWT token if authentication is successful, empty otherwise.
     */
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
    /**
     * Retrieve a doctor by their email address.
     *
     * @param emailAddress The email address of the doctor to retrieve.
     * @return The doctor with the specified email address.
     */
    public Doctor findDoctorByEmailAddress(String emailAddress) {
        return doctorRepository.findDoctorByEmailAddress(emailAddress);
    }
    /**
     * Create a new patient for the current logged-in doctor.
     *
     * @param patientObject The patient object to create.
     * @return Optional containing the created patient, or empty if a patient with the same details already exists.
     * @throws InformationExistException If a patient with the same details already exists.
     */

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

    /**
     * Get all patients belonging to the current logged-in doctor.
     *
     * @return The list of all patients for the current doctor.
     */
    //http://localhost:9092/api/doctors/1/patients/
    public List<Patient> getAllPatients(Long doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            List<Patient> patientList = doctorOptional.get().getPatientList();
            return patientList;
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }
    /**
     * Get a patient by ID for the current logged-in doctor.
     *
     * @param patientId The ID of the patient to retrieve.
     * @return Optional containing the patient if found, or empty if not found.
     */
    public Optional<Patient> getPatientById(Long doctorId, Long patientId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = patientRepository.findById(patientId);
            return patientOptional;
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }
    /**
     * Update a patient's details for the current logged-in doctor.
     *
     * @param patientId      The ID of the patient to update.
     * @param patientObject  The updated patient object.
     * @return Optional containing the updated patient, or empty if the patient was not found.
     */
    public Optional<Patient> updatePatientById(Long doctorId, Long patientId, Patient patientObject) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = patientRepository.findById(patientId);
            patientOptional.get().setName(patientObject.getName());
            patientOptional.get().setBirthDate(patientObject.getBirthDate());
            patientRepository.save(patientOptional.get());
            return patientOptional;
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }
    /**
     * Delete a patient for the current logged-in doctor.
     *
     * @param patientId The ID of the patient to delete.
     * @return Optional containing the deleted patient, or empty if the patient was not found.
     */
    public Optional<Patient> deletePatientById(Long doctorId, Long patientId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = patientRepository.findById(patientId);
            patientRepository.deleteById(patientId);
            return patientOptional;
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }
    /**
     * Create a new prescription for a patient of the current logged-in doctor.
     *
     * @param patientId        The ID of the patient for whom to create the prescription.
     * @param prescriptionObject  The prescription object to create.
     * @return Optional containing the created prescription, or empty if a prescription with the same details already exists for the patient.
     */
    public Optional<Prescription> createPrescriptionPatient(Long doctorId, Long patientId, Prescription prescriptionObject) {
        Optional<Patient> patientOptional = Optional.ofNullable(patientRepository.findByDoctorId(doctorId));

        if (patientOptional.isEmpty()) {
            throw new InformationNotFoundException("Patient with id " + patientId + " does not exist");
        } else {
            Optional<Prescription> prescriptionOptional =
                    prescriptionRepository.findByPatientIdAndDetails(patientId, prescriptionObject.getDetails());
            if (prescriptionOptional.isEmpty()) {
                prescriptionObject.setDoctor(DoctorService.getCurrentLoggedInDoctor());
                prescriptionObject.setPatient(patientOptional.get());
                return Optional.of(prescriptionRepository.save(prescriptionObject));

            } else {
                return Optional.empty();
            }
        }

    }

    /**
     * Get all prescriptions for a patient of the current logged-in doctor.
     *
     * @param patientId The ID of the patient for whom to retrieve prescriptions.
     * @return The list of prescriptions for the specified patient.
     */
// GET ALL PRESCRIPTIONS
    public List<Prescription> getAllPrescriptionsPatient(Long doctorId, Long patientId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = Optional.of(patientRepository.findByDoctorId(doctorId));
            if (patientOptional.isPresent()) {
                return patientOptional.get().getPrescriptionList();
            } else {
                throw new InformationNotFoundException("Patient with ID " + patientId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    // GET  PRESCRIPTION BY ID

    /**
     * Get a prescription by ID for a patient of the current logged-in doctor.
     *
     * @param doctorId       The ID of the current logged-in doctor.
     * @param patientId      The ID of the patient for whom to retrieve the prescription.
     * @param prescriptionId The ID of the prescription to retrieve.
     * @return Optional containing the prescription if found, or empty if not found.
     * @throws InformationNotFoundException If the doctor, patient, or prescription is not found.
     */

    public Optional<Prescription> getPrescriptionById(Long doctorId, Long patientId, Long prescriptionId ) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = Optional.of(patientRepository.findByDoctorId(doctorId));
            if (patientOptional.isPresent()) {
                Optional<Prescription> prescriptionOptional = prescriptionRepository.findByPatientIdAndId(patientId, prescriptionId);
                return prescriptionOptional;
            } else {
                throw new InformationNotFoundException("Patient with ID " + patientId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    // UPDATE PRESCRIPTION BY ID

    /**
     * Update a prescription by ID for a patient of the current logged-in doctor.
     *
     * @param doctorId       The ID of the current logged-in doctor.
     * @param patientId      The ID of the patient for whom the prescription belongs.
     * @param prescriptionId The ID of the prescription to update.
     * @param prescriptionObject The updated prescription object.
     * @return Optional containing the updated prescription if successful, or empty if the prescription or patient is not found.
     * @throws InformationNotFoundException If the doctor, patient, or prescription is not found.
     */

    public Optional<Prescription> updatePrescriptionById(Long doctorId, Long patientId, Long prescriptionId, Prescription prescriptionObject) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = Optional.of(patientRepository.findByDoctorId(doctorId));
            if (patientOptional.isPresent()) {
                Optional<Prescription> prescriptionOptional = prescriptionRepository.findByPatientIdAndId(patientId, prescriptionId);
                if (prescriptionOptional.isPresent()) {
                    prescriptionOptional.get().setDetails(prescriptionObject.getDetails());
                  return Optional.of(prescriptionRepository.save(prescriptionOptional.get()));
                } else {
                    return Optional.empty();
                }
            } else {
                throw new InformationNotFoundException("Patient with ID " + patientId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }
    /**
     * Delete a prescription by ID for a patient of the current logged-in doctor.
     *
     * @param doctorId       The ID of the current logged-in doctor.
     * @param patientId      The ID of the patient for whom the prescription belongs.
     * @param prescriptionId The ID of the prescription to delete.
     * @return Optional containing the deleted prescription if successful, or empty if the prescription or patient is not found.
     * @throws InformationNotFoundException If the doctor, patient, or prescription is not found.
     */
    public Optional<Prescription> deletePrescriptionById(Long doctorId, Long patientId, Long prescriptionId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isPresent()) {
            Optional<Patient> patientOptional = Optional.of(patientRepository.findByDoctorId(doctorId));
            if (patientOptional.isPresent()) {
                Optional<Prescription> prescriptionOptional = prescriptionRepository.findByPatientIdAndId(patientId, prescriptionId);
                if (prescriptionOptional.isPresent()) {
                    prescriptionRepository.deleteById(prescriptionOptional.get().getId());
                    return prescriptionOptional;
                } else {
                    return Optional.empty();
                }
            } else {
                throw new InformationNotFoundException("Patient with ID " + patientId + " not found");
            }
        } else {
            throw new InformationNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }
}
