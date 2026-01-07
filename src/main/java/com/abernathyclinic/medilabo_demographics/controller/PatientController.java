package com.abernathyclinic.medilabo_demographics.controller;

import com.abernathyclinic.medilabo_demographics.model.Patient;
import com.abernathyclinic.medilabo_demographics.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    public List<Patient> getAllPatients() {
        log.info("Http Get request received at/ patient URL");
        return patientService.getAllPatients();
    }

    @PostMapping
    public ResponseEntity<String> addPatient(@RequestBody Patient patient) {
        log.info("POST /api/patient - Adding patient {} {} {}",
                patient.getFirstName(), patient.getLastName(), patient.getBirthdate());
        patientService.addPatient(patient);
        return ResponseEntity.ok("Patient added successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Integer id) {
        log.info("Get request received to find a patient with ID: {}", id);
        Patient patient = patientService.getById(id);

        if (patient == null) {
            log.info("Patient not found:{}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable Integer id,
                                                @RequestBody Patient updatePatient) {
        log.info("PUT request received to update patient with ID:{}", id);

        boolean updated = patientService.updatePatient(id, updatePatient);

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Patient updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
        log.info("DELETE /api/patient/{}", id);

        boolean deleted = patientService.deletePatient(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Patient deleted successfully.");
    }
}