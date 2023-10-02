package com.example.jvshealth.controller;

import com.example.jvshealth.models.Doctor;
import com.example.jvshealth.models.Patient;
import com.example.jvshealth.models.Prescription;
import com.example.jvshealth.repository.DoctorRepository;
import com.example.jvshealth.request.LoginRequest;
import com.example.jvshealth.response.LoginResponse;
import com.example.jvshealth.security.MyDoctorDetails;
import com.example.jvshealth.security.MyDoctorDetailsService;
import com.example.jvshealth.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.mockito.Mockito;


import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


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

    @MockBean
    private MyDoctorDetailsService myDoctorDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
                .andDo(print());
    }

    Patient PATIENT_1 = new Patient(1L, "Merrill Huang", LocalDate.of(2023,9,25));

    Patient PATIENT_2 = new Patient(2L, "Shayla White", LocalDate.of(2023,10,25));

    Patient PATIENT_3 = new Patient(3L, "Ariadna Rubio", LocalDate.of(2023,11,25));

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void createPatientDoctor() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        // Mock the behavior of the doctorService to return a specific patient
        when(doctorService.createPatientDoctor(Mockito.any(Long.class), Mockito.any(Patient.class)))
                .thenReturn(Optional.ofNullable(PATIENT_2));

        MyDoctorDetails doctorDetails = setup();

        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        // Prepare and perform the POST request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/doctors/patients/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PATIENT_2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void getAllPatients() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        MyDoctorDetails doctorDetails = setup();

        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        List<Patient> patientList = Arrays.asList(PATIENT_1, PATIENT_2, PATIENT_3);

        when(doctorService.getAllPatients(Mockito.any(Long.class))).thenReturn(patientList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/patients/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void getPatientById() throws Exception {

        objectMapper.registerModule(new JavaTimeModule());

        MyDoctorDetails doctorDetails = setup();
        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        when(doctorService.getPatientById(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(Optional.ofNullable(PATIENT_3));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/patients/3/")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value(PATIENT_3.getId()))
                .andExpect(jsonPath("$.data.name").value(PATIENT_3.getName()))
                .andExpect((jsonPath("$.data.birthDate")).value(PATIENT_3.getBirthDate().toString()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void updatePatientById() throws Exception {

        objectMapper.registerModule(new JavaTimeModule());

        MyDoctorDetails doctorDetails = setup();
        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        Patient updatePatient = new Patient(1L, "Dhrubo", LocalDate.of(2023, 10, 2));

        when(doctorService.updatePatientById(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Patient.class))).thenReturn(Optional.of(updatePatient));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/doctors/patients/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PATIENT_1))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("successfully updated!"))
                .andExpect(jsonPath("$.data.id").value(updatePatient.getId()))
                .andExpect(jsonPath("$.data.name").value(updatePatient.getName()))
                .andExpect((jsonPath("$.data.birthDate")).value(updatePatient.getBirthDate().toString()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void deletePatientById() throws Exception {

        objectMapper.registerModule(new JavaTimeModule());

        MyDoctorDetails doctorDetails = setup();
        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        when(doctorService.deletePatientById(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(Optional.of(PATIENT_1));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/doctors/patients/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("successfully deleted!"))
                .andExpect(jsonPath("$.data.id").value(PATIENT_1.getId()))
                .andExpect(jsonPath("$.data.name").value(PATIENT_1.getName()))
                .andExpect((jsonPath("$.data.birthDate")).value(PATIENT_1.getBirthDate().toString()))
                .andDo(print());
    }

    Prescription PRESCRIPTION_1 = new Prescription(1L, RECORD_1, PATIENT_1, "Flu shot");

    Prescription PRESCRIPTION_2 = new Prescription(2L, RECORD_2, PATIENT_3, "Bird Flu shot");

    Prescription PRESCRIPTION_3 = new Prescription(2L, RECORD_2, PATIENT_3, "Swine Flu shot");

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void createPrescriptionPatient() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        // Mock the behavior of the doctorService to return a specific patient
        when(doctorService.createPrescriptionPatient(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Prescription.class)))
                .thenReturn(Optional.ofNullable(PRESCRIPTION_1));

        MyDoctorDetails doctorDetails = setup();

        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        // Prepare and perform the POST request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/doctors/patients/1/prescriptions/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PRESCRIPTION_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value(PRESCRIPTION_1.getId()))
                .andExpect(jsonPath("$.data.details").value(PRESCRIPTION_1.getDetails()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void getAllPrescriptionsPatient() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        MyDoctorDetails doctorDetails = setup();

        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        List<Prescription> prescriptionList = Arrays.asList(PRESCRIPTION_1, PRESCRIPTION_2, PRESCRIPTION_3);

        when(doctorService.getAllPrescriptionsPatient(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(prescriptionList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/patients/2/prescriptions/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void getPrescriptionById() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        MyDoctorDetails doctorDetails = setup();

        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        when(doctorService.getPrescriptionById(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(Optional.ofNullable(PRESCRIPTION_3));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/patients/2/prescriptions/3/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value(PRESCRIPTION_3.getId()))
                .andExpect(jsonPath("$.data.details").value(PRESCRIPTION_3.getDetails()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "suresh@ga.com")
    public void updatePrescriptionById() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());

        MyDoctorDetails doctorDetails = setup();

        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myDoctorDetailsService.loadUserByUsername("suresh@ga.com")).thenReturn(doctorDetails);

        Prescription updatePrescription = new Prescription(1L, RECORD_2, PATIENT_2,"Pfizer");

        when(doctorService.updatePrescriptionById(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Prescription.class))).thenReturn(Optional.ofNullable(updatePrescription));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/doctors/patients/2/prescriptions/1/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .content(objectMapper.writeValueAsString(updatePrescription))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("successfully updated!"))
                .andExpect(jsonPath("$.data.id").value(updatePrescription.getId()))
                .andExpect(jsonPath("$.data.details").value(updatePrescription.getDetails()))
                .andDo(print());
    }

    private String generateJwtToken() {
        // Create a JWT token with a specific subject and expiration time
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject("suresh@ga.com")
                .setExpiration(new Date((new Date()).getTime() + 86400000))
                .signWith(SignatureAlgorithm.HS256, "C6UlILsE6GJwNqwCTkkvJj9O653yJUoteWMLfYyrc3vaGrrTOrJFAUD1wEBnnposzcQl");
        return jwtBuilder.compact();
    }

    private MyDoctorDetails setup() {

        // Create a Doctor object with known properties
        Doctor sureshRecord = new Doctor(1L, "Suresh", "Sigera", "suresh@ga.com");
        sureshRecord.setPassword("password");

        return new MyDoctorDetails(sureshRecord);
    }
}