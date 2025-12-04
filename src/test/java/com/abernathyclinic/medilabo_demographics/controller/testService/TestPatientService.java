package com.abernathyclinic.medilabo_demographics.controller.testService;

import com.abernathyclinic.medilabo_demographics.model.Patient;
import com.abernathyclinic.medilabo_demographics.repository.PatientRepository;
import com.abernathyclinic.medilabo_demographics.service.PatientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestPatientService {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;
    private Patient samplePatient;

    @BeforeEach
    void setUp() {
        samplePatient = new Patient();
        samplePatient.setId(1);
        samplePatient.setLastName("Gonzales");
        samplePatient.setFirstName("Dilan");
        samplePatient.setBirthdate(LocalDate.of(1985, 12, 10));
        samplePatient.setGender('F');
        samplePatient.setAddress("123 Oak Lead Dr");
        samplePatient.setPhone("222-888-9999");
    }

    @Test
    void testGetAllPatients_FromRepository() {
        Mockito.when(patientRepository.findAll()).thenReturn(List.of(samplePatient));

        List<Patient> result = patientService.getAllPatients();
        assertEquals(1, result.size());
        assertEquals("Dilan", result.get(0).getFirstName());
        verify(patientRepository).findAll();

    }

    @Test
    void testAddPatient() {
        patientService.addPatient(samplePatient);
        verify(patientRepository).save(samplePatient);
    }

    @Test
    void testGetById_WhenPatientFound(){
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(samplePatient));

        Patient result =patientService.getById(1);
        assertNotNull(result);
        assertEquals("Gonzales", result.getLastName());
        verify(patientRepository).findById(1);
    }

    @Test
    void testGetById_WhenNotFound(){
        Mockito.when(patientRepository.findById(2)).thenReturn(Optional.empty());

        Patient result =patientService.getById(2);
        assertNull(result);
        verify(patientRepository).findById(2);
    }

    @Test
    void testUpdatePatient_WhenPatientFound() {
        Patient updated = new Patient();
        updated.setFirstName("Dilan");
        updated.setLastName("Gonzales");
        updated.setBirthdate(LocalDate.of(1985, 12, 10));
        updated.setAddress("456 Elm St");
        updated.setPhone("555-777-5577");

        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(samplePatient));

        boolean result = patientService.updatePatient(1, updated);

        assertTrue(result);
        assertEquals("Dilan", samplePatient.getFirstName());
        verify(patientRepository).save(samplePatient);
    }

    @Test
    void testUpdatePatient_WhenPatientNotFound() {
        Mockito.when(patientRepository.findById(66)).thenReturn(Optional.empty());

        boolean result = patientService.updatePatient(66, samplePatient);

        assertFalse(result);
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testDeletePatient_WhenPatientFound() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(samplePatient));

        boolean result = patientService.deletePatient(1);

        assertTrue(result);
        verify(patientRepository).delete(samplePatient);
    }

    @Test
    void testDeletePatient_WhenPatientNotFound() {
        Mockito.when(patientRepository.findById(99)).thenReturn(Optional.empty());

        boolean result = patientService.deletePatient(99);

        assertFalse(result);
        verify(patientRepository, never()).delete(any());
    }
}