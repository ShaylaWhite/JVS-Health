package com.example.jvshealth.controller;

import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/doctors/")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

}
