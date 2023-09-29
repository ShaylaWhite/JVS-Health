package com.example.jvshealth.controller;

import com.example.jvshealth.models.Patient;
import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/doctors/")
public class DoctorController {

    private PatientService patientService;

    private PrescriptionService prescriptionService;

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setPrescriptionService(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

    @GetMapping(path = "/{doctorId}/patients/")
    public ResponseEntity<?> getAllPatients() {
        List<Patient> patientList = doctorService.getAllPatients();
        }
    }
}
