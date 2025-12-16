package com.abernathyclinic.medilabo_demographics.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false) // disable security filters
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

//    @Test
//    void testHelloEndpoint() throws Exception {
//        mockMvc.perform(get("/api/patient/hello"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Hi, you are login to Patient service" + "anonymousUser" + "!"));
//    }
//
//    @Test
//    void testSecretEndpoint_Admin() throws Exception {
//        mockMvc.perform(get("/api/patient/admin/secret"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Admin area (Patient Service), anonymousUser"));
//    }

    @Test
    void testGetAllPatients() throws Exception {
        Mockito.when(patientService.getAllPatients()).thenReturn(List.of(samplePatient));

        mockMvc.perform(get("/api/patient/all")
                        .header("Origin", "http://localhost:8082"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Smith"));
    }

    @Test
    void testAddPatient() throws Exception {
        Mockito.doNothing().when(patientService).addPatient(ArgumentMatchers.any(Patient.class));

        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(samplePatient)))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient added successfully."));
    }

    @Test
    void testGetPatientByIdNotFound() throws Exception {
        Mockito.when(patientService.getById(99)).thenReturn(null);

        mockMvc.perform(get("/api/patient/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPatientByIdFound() throws Exception {
        Mockito.when(patientService.getById(1)).thenReturn(samplePatient);

        mockMvc.perform(get("/api/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void testUpdatePatient() throws Exception {
        Mockito.when(patientService.updatePatient(eq(1), ArgumentMatchers.any(Patient.class))).thenReturn(true);

        mockMvc.perform(put("/api/patient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(samplePatient)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testDeletePatient_Success() throws Exception {
        Mockito.when(patientService.deletePatient(1)).thenReturn(true);

        mockMvc.perform(delete("/api/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient deleted successfully."));
    }

    @Test
    void testDeletePatient_NotFound() throws Exception {
        Mockito.when(patientService.deletePatient(99)).thenReturn(false);

        mockMvc.perform(delete("/api/patient/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("Person not found!"));
    }
}