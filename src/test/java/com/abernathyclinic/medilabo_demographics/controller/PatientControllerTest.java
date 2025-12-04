package com.abernathyclinic.medilabo_demographics.controller;

import com.abernathyclinic.medilabo_demographics.model.Patient;
import com.abernathyclinic.medilabo_demographics.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient samplePatient;

    @BeforeEach
    void setUp() {
        samplePatient = new Patient();
        samplePatient.setId(1);
        samplePatient.setLastName("Smith");
        samplePatient.setFirstName("Sarah");
        samplePatient.setBirthdate(LocalDate.of(1980, 12, 23));
        samplePatient.setGender('F');
        samplePatient.setAddress("123 Oak Lead Dr");
        samplePatient.setPhone("100-222-3333");
    }

    @Test
    void testGetAllPatients() throws Exception {
        Mockito.when(patientService.getAllPatients()).thenReturn(List.of(samplePatient));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/all")
                        .header("Origin", "http://localhost:8082"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Smith"));
    }

    @Test
    void testAddPatient() throws Exception {
        doNothing().when(patientService).addPatient(any(Patient.class));

        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(samplePatient)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Patient added successfully."));
    }

    @Test
    void testGetPatientByIdNotFound() throws Exception {
        Mockito.when(patientService.getById(99)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePatient() throws Exception {
        Mockito.when(patientService.updatePatient(eq(1), ArgumentMatchers.any(Patient.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/patient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(samplePatient)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }
}