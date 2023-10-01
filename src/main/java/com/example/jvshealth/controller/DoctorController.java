package com.example.jvshealth.controller;

import com.example.jvshealth.models.Patient;
import com.example.jvshealth.models.Prescription;
import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/doctors/")
public class DoctorController {

    private DoctorService doctorService;

    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }



    static HashMap<String, Object> message = new HashMap<>();

    @PostMapping(path = "/patients/")
    public ResponseEntity<?> createPatientDoctor(@RequestBody Patient patientObject) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        Optional<Patient> patientOptional = doctorService.createPatientDoctor(doctorId, patientObject);
        if (patientOptional.isPresent()) {
            message.put("message", "success");
            message.put("data", patientOptional.get());
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "Patient already exists");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path = "/patients/")
    public ResponseEntity<?> getAllPatients() {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        List<Patient> patientList = doctorService.getAllPatients(doctorId);
        if (patientList.isEmpty()) {
            message.put("message", "No patients found for doctor with id " + doctorId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", patientList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
    @GetMapping(path = "/patients/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable(value = "patientId") Long patientId) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
       Optional<Patient> patientOptional = doctorService.getPatientById(doctorId, patientId);
        if (patientOptional.isEmpty()) {
            message.put("message", "No patient with patient id " + patientId + " found" );
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", patientOptional);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

@PutMapping(path = "/patients/{patientId}")
 public ResponseEntity<?>  updatePatientById(@PathVariable(value = "patientId") Long patientId ,@RequestBody Patient patientObject){
    Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
    Optional<Patient> patientOptional = doctorService.updatePatientById(doctorId, patientId, patientObject);
    if (patientOptional.isEmpty()) {
        message.put("message", "No patient with patient id " + patientId + " found" );
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    } else {
        message.put("message", "successfully updated!");
        message.put("data", patientOptional);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

    @DeleteMapping(path = "/patients/{patientId}")
    public ResponseEntity<?> deletePatientById(@PathVariable(value = "patientId") Long patientId ){
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        Optional<Patient> patientOptional = doctorService.deletePatientById(doctorId, patientId);
        if (patientOptional.isEmpty()) {
            message.put("message", "No patient with patient id " + patientId + " found" );
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "successfully deleted!");
            message.put("data", patientOptional);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    //CRUD FOR PRESCRIPTION
    @PostMapping(path = "/patients/{patientId}/prescriptions/")
    public ResponseEntity<?> createPrescriptionPatient(@PathVariable(value = "patientId") Long patientId,@RequestBody Prescription prescriptionObject) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        Optional<Prescription> prescriptionOptional =
                doctorService.createPrescriptionPatient(doctorId,patientId, prescriptionObject);
        if (prescriptionOptional.isPresent()) {
            message.put("message", "success");
            message.put("data", prescriptionOptional.get());
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "Prescription already exists");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }
    @GetMapping(path = "/patients/{patientId}/prescriptions/")
    public ResponseEntity<?> getAllPrescriptionsPatients(@PathVariable(value="patientId") Long patientId) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        List<Prescription> prescriptionList = doctorService.getAllPrescriptionsPatients(doctorId, patientId);
        if (prescriptionList.isEmpty()) {
            message.put("message", "No prescriptions found for doctor with id " + doctorId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", prescriptionList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
    @GetMapping(path = "/patients/{patientId}/prescriptions/{prescriptionId}")
    public ResponseEntity<?> getPrescriptionById(@PathVariable(value="patientId") Long patientId, @PathVariable(value="prescriptionId") Long prescriptionId) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        Optional <Prescription> prescriptionOptional = doctorService.getPrescriptionById(doctorId, patientId, prescriptionId);
        if (prescriptionOptional .isEmpty()) {
            message.put("message", "No prescriptions with id" + prescriptionId + "found for" + doctorId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data",  prescriptionOptional );
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
    @PutMapping(path = "/patients/{patientId}/prescriptions/{prescriptionId}")
    public ResponseEntity<?>  updatePrescriptionById(@PathVariable(value="patientId") Long patientId, @PathVariable(value="prescriptionId") Long prescriptionId, @RequestBody Prescription prescriptionObject) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        Optional<Prescription> prescriptionOptional = doctorService.updatePrescriptionById(doctorId, patientId, prescriptionId, prescriptionObject);
        if (prescriptionOptional.isEmpty()) {
            message.put("message", "No prescription with prescription id " + prescriptionId + " found" );
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "successfully updated!");
            message.put("data", prescriptionOptional);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/patients/{patientId}/prescriptions/{prescriptionId}")
    public ResponseEntity<?>  deletePrescriptionById(@PathVariable(value="patientId") Long patientId, @PathVariable(value="prescriptionId") Long prescriptionId) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        Optional<Prescription> prescriptionOptional = doctorService.deletePrescriptionById(doctorId, patientId, prescriptionId);
        if (prescriptionOptional.isEmpty()) {
            message.put("message", "No prescription with prescription id " + prescriptionId + " found" );
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "successfully deleted!");
            message.put("data", prescriptionOptional);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

}


