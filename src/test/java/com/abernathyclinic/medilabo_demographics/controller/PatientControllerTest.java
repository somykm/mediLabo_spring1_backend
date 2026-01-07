//package com.abernathyclinic.medilabo_demographics.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import com.abernathyclinic.medilabo_demographics.model.Patient;
//import com.abernathyclinic.medilabo_demographics.service.PatientService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import org.mockito.Mockito;
//
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//class PatientControllerTest {
//
//    @Mock
//    private PatientService patientService;
//
//    @InjectMocks
//    private PatientController patientController;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    private Patient samplePatient;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
//
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        samplePatient = new Patient();
//        samplePatient.setId(1);
//        samplePatient.setLastName("Smith");
//        samplePatient.setFirstName("Sarah");
//        samplePatient.setBirthdate(LocalDate.of(1980, 12, 23));
//        samplePatient.setGender('F');
//        samplePatient.setAddress("123 Oak Lead Dr");
//        samplePatient.setPhone("100-222-3333");
//    }
//
//    @Test
//    void testGetAllPatients() throws Exception {
//        Mockito.when(patientService.getAllPatients()).thenReturn(List.of(samplePatient));
//
//        mockMvc.perform(get("/api/patient/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].lastName").value("Smith"));
//    }
//
//    @Test
//    void testAddPatient() throws Exception {
//        Mockito.doNothing().when(patientService).addPatient(any(Patient.class));
//
//        mockMvc.perform(post("/api/patient")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(samplePatient)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Patient added successfully."));
//    }
//
//    @Test
//    void testGetPatientByIdNotFound() throws Exception {
//        Mockito.when(patientService.getById(99)).thenReturn(null);
//
//        mockMvc.perform(get("/api/patient/99"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testGetPatientByIdFound() throws Exception {
//        Mockito.when(patientService.getById(1)).thenReturn(samplePatient);
//
//        mockMvc.perform(get("/api/patient/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.lastName").value("Smith"));
//    }
//
//    @Test
//    void testUpdatePatient() throws Exception {
//        Mockito.when(patientService.updatePatient(eq(1), any(Patient.class)))
//                .thenReturn(true);
//
//        mockMvc.perform(put("/api/patient/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(samplePatient)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Patient updated successfully."));
//    }
//
//    @Test
//    void testDeletePatient_Success() throws Exception {
//        Mockito.when(patientService.deletePatient(1)).thenReturn(true);
//
//        mockMvc.perform(delete("/api/patient/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Patient deleted successfully."));
//    }
//
//    @Test
//    void testDeletePatient_NotFound() throws Exception {
//        Mockito.when(patientService.deletePatient(99)).thenReturn(false);
//
//        mockMvc.perform(delete("/api/patient/99"))
//                .andExpect(status().isNotFound());
//    }
//}