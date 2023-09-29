package com.example.jvshealth.controller;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.repository.DoctorRepository;
import com.example.jvshealth.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @InjectMocks
    @MockBean
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this); //without this you will get NPE
    }


    Doctor RECORD_1 = new Doctor(1L, "Merrill", "Huang", "merrill@ga.com");

    Doctor RECORD_2 = new Doctor(2L, "Shayla", "White", "shayla@ga.com");

    Doctor RECORD_3 = new Doctor(3L, "Ariadna", "Rubio", "ariadna@ga.com");

    @Test
    public void createDoctor() throws Exception {
        when(doctorService.createDoctor(RECORD_1)).thenReturn(RECORD_1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/auth/doctors/register/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(RECORD_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value((RECORD_1.getId())))
                .andExpect(jsonPath("$.firstName").value(RECORD_1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(RECORD_1.getLastName()))
                .andExpect(jsonPath("$.emailAddress").value(RECORD_1.getEmailAddress()))
                .andDo(print());

    }

    @Test
    public void loginUser() {
    }
}