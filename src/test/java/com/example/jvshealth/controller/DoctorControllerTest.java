package com.example.jvshealth.controller;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.repository.DoctorRepository;
import com.example.jvshealth.request.LoginRequest;
import com.example.jvshealth.response.LoginResponse;
import com.example.jvshealth.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example")
public class DoctorControllerTest {

    @Autowired
    @MockBean
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PasswordEncoder passwordEncoder;

    Doctor RECORD_1 = new Doctor(1L, "Merrill", "Huang", "merrill@ga.com");

    Doctor RECORD_2 = new Doctor(2L, "Shayla", "White", "shayla@ga.com");

    Doctor RECORD_3 = new Doctor(3L, "Ariadna", "Rubio", "ariadna@ga.com");

    @Test
    public void createDoctor() throws Exception {
        when(doctorService.createDoctor(Mockito.any(Doctor.class))).thenReturn(RECORD_1);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/doctors/register/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RECORD_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value((RECORD_1.getId())))
                .andExpect(jsonPath("$.firstName").value(RECORD_1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(RECORD_1.getLastName()))
                .andExpect(jsonPath("$.emailAddress").value(RECORD_1.getEmailAddress()))
                .andDo(print());

    }

    @Test
    public void loginUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmailAddress("suresh123@gmail.com");
        request.setPassword("suresh123");
        Optional<String> token = Optional.of("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWVyc2gxMjNAZ21haWwuY29tIiwiaWF0IjoxNjk2MDA4NTg5LCJleHAiOjE2OTYwOTQ5ODl9.nJDx67WgI5JZiFL_LFz4uFxFVgOR_nVPMCbrcY8Dcx8");
        when(doctorService.loginDoctor(Mockito.any(LoginRequest.class))).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/doctors/login/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value(token.get()))
                .andDo(print());;
    }
}