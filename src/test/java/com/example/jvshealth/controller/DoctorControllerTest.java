package com.example.jvshealth.controller;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    ObjectMapper objectMapper;

    Doctor RECORD_1 = new Doctor(1L, "Merrill", "Huang", "merrill@ga.com");

    Doctor RECORD_2 = new Doctor(2L, "Shayla", "White", "shayla@ga.com");

    Doctor RECORD_3 = new Doctor(3L, "Ariadna", "Rubio", "ariadna@ga.com");

    @Test
    public void createDoctor() {

    }

    @Test
    public void loginUser() {
    }
}