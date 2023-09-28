package com.example.jvshealth.controller;

import com.example.jvshealth.service.DoctorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/doctors/")
public class DoctorController {
    private DoctorService doctorService;
}
