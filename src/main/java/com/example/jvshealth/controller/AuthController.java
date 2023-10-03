package com.example.jvshealth.controller;


import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.request.LoginRequest;
import com.example.jvshealth.response.LoginResponse;
import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import java.util.HashMap;

/**
 * The AuthController class handles authentication operations for doctors in the JvsHealth application.
 *
 * @RestController indicates that this class is a Spring REST Controller.
 * @RequestMapping specifies the base URL path for all endpoints in this controller.
 */
@RestController
@RequestMapping(path = "/auth/doctors/")    // http://localhost:9092/auth
public class AuthController {

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
    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

//CREATE USER
    /**
     * Endpoint for registering a new doctor.
     *
     * @param doctorObject The Doctor object representing the doctor to be registered.
     * @return The registered Doctor object.
     */
    @PostMapping("/register") // http://localhost:9092/auth/doctors/register/
    public Doctor createDoctor(@RequestBody Doctor doctorObject) {
        return doctorService.createDoctor(doctorObject);
    }
//LOGIN USER (Post)
    /**
     * Endpoint for authenticating and logging in a doctor.
     *
     * @param loginRequest The LoginRequest object containing login credentials.
     * @return A ResponseEntity containing a LoginResponse with a JWT token upon successful authentication.
     *         Returns UNAUTHORIZED status with an error message if authentication fails.
     */
    @PostMapping(path = "/login/") // http://localhost:9092/auth/doctors/login/
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<String> jwtToken = doctorService.loginDoctor(loginRequest);
        if (jwtToken.isPresent()) {
            return ResponseEntity.ok(new LoginResponse(jwtToken.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Authentication failed"));
        }
    }
}