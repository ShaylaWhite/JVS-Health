package com.example.jvshealth.controller;


import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.request.LoginRequest;
import com.example.jvshealth.response.LoginResponse;
import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/auth/doctors/")    // http://localhost:9092/auth
public class DoctorController {

    private DoctorService doctorService;

    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
static HashMap<String, Object> result = new HashMap<>();
static HashMap<String, Object> message = new HashMap<>();

//CREATE USER

    @PostMapping("/register") // http://localhost:9092/auth/doctors/register/
    public Doctor createDoctor(@RequestBody Doctor doctorObject) {
        return doctorService.createDoctor(doctorObject);
    }


// LOGIN USER (POST)
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
