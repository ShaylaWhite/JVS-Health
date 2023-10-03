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
/**
 * The DoctorController class handles doctor-specific operations in the JvsHealth application.
 *
 * @RestController indicates that this class is a Spring REST Controller.
 * @RequestMapping specifies the base URL path for all endpoints in this controller.
 */
@RestController
@RequestMapping(path = "/api/doctors/")
public class DoctorController {

    private DoctorService doctorService;
    /**
     * Constructor-based autowiring of the DoctorService dependency.
     *
     * @param doctorService The DoctorService implementation to be injected.
     */
    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }



    static HashMap<String, Object> message = new HashMap<>();
    /**
     * Endpoint for creating a new patient under the current doctor.
     *
     * @param patientObject The Patient object representing the patient to be created.
     * @return A ResponseEntity indicating success or conflict along with a message and data.
     */
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
    /**
     * Endpoint for retrieving all patients under the current doctor.
     *
     * @return A ResponseEntity containing a list of patients or a not found message.
     */
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
    /**
     * Endpoint for retrieving a patient by their ID under the current doctor.
     *
     * @param patientId The ID of the patient to retrieve.
     * @return A ResponseEntity containing the patient data or a not found message.
     */
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
    /**
     * Endpoint for updating a patient's information by their ID under the current doctor.
     *
     * @param patientId     The ID of the patient to update.
     * @param patientObject The updated Patient object.
     * @return A ResponseEntity indicating success or not found along with a message and data.
     */
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
    /**
     * Endpoint for deleting a patient by their ID under the current doctor.
     *
     * @param patientId The ID of the patient to delete.
     * @return A ResponseEntity indicating success or not found along with a message and data.
     */
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
    /**
     * Endpoint for creating a prescription for a specific patient under the current doctor.
     *
     * @param patientId        The ID of the patient for whom the prescription is created.
     * @param prescriptionObject The Prescription object representing the prescription to be created.
     * @return A ResponseEntity indicating success or conflict along with a message and data.
     */
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

    /**
     * Endpoint for retrieving all prescriptions for a specific patient under the current doctor.
     *
     * @param patientId The ID of the patient for whom prescriptions are retrieved.
     * @return A ResponseEntity containing a list of prescriptions or a not found message.
     */

    @GetMapping(path = "/patients/{patientId}/prescriptions/")
    public ResponseEntity<?> getAllPrescriptionsPatient(@PathVariable(value="patientId") Long patientId) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        List<Prescription> prescriptionList = doctorService.getAllPrescriptionsPatient(doctorId, patientId);
        if (prescriptionList.isEmpty()) {
            message.put("message", "No prescriptions found for doctor with id " + doctorId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", prescriptionList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
    /**
     * Endpoint for retrieving a prescription by its ID for a specific patient under the current doctor.
     *
     * @param patientId     The ID of the patient for whom the prescription belongs.
     * @param prescriptionId The ID of the prescription to retrieve.
     * @return A ResponseEntity containing the prescription data or a not found message.
     */
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
    /**
     * Endpoint for updating a prescription by its ID for a specific patient under the current doctor.
     *
     * @param patientId         The ID of the patient for whom the prescription belongs.
     * @param prescriptionId    The ID of the prescription to update.
     * @param prescriptionObject The updated Prescription object.
     * @return A ResponseEntity indicating success or not found along with a message and data.
     */
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
    /**
     * Endpoint for deleting a prescription by its ID for a specific patient under the current doctor.
     *
     * @param patientId      The ID of the patient for whom the prescription belongs.
     * @param prescriptionId The ID of the prescription to delete.
     * @return A ResponseEntity indicating success or not found along with a message and data.
     */
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


