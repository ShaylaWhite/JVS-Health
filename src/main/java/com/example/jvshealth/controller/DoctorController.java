package com.example.jvshealth.controller;

import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/auth/doctors/") //http:localhost
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
static HashMap<String, Object> result = new HashMap<>();
static HashMap<String, Object> message = new HashMap<>();

@PostMapping(path = "register/")


@PostMapping(path = "login/")
    <ResponseEnitity>
}
