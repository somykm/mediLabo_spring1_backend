package com.abernathyclinic.medilabo_demographics.service;

import com.abernathyclinic.medilabo_demographics.model.Patient;
import com.abernathyclinic.medilabo_demographics.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    public List<Patient> getAllPatients() {
        log.info("Fetching all patients");
        return patientRepository.findAll();
    }
    public void addPatient(Patient patient) {
        log.info("Adding new patient: {} {}", patient.getFirstName(), patient.getLastName());
        patientRepository.save(patient);

    }

    public Patient getById(Integer id) {
        log.info("Fetching patient by id:{}", id);
        return patientRepository.findById(id).orElse(null);
    }

    public boolean updatePatient(Integer id, Patient updatePatient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setFirstName(updatePatient.getFirstName());
            patient.setLastName(updatePatient.getLastName());
            patient.setBirthdate(updatePatient.getBirthdate());
            patient.setAddress(updatePatient.getAddress());
            patient.setPhone(updatePatient.getPhone());

            patientRepository.save(patient);
            log.debug("Patient information updated: {}", patient);
            return true;
        }
        log.warn("No patient found based on provided info for update: {}", id);
        return false;
    }

    public boolean deletePatient(Integer id) {
        Optional<Patient> patients = patientRepository.findById(id);

        if (patients.isPresent()) {
            Patient patient = patients.get();
            patientRepository.delete(patient);
            log.debug("Patient deleted successfully!: {}", patient);
            return true;
        }

        log.warn("No patient found for deletion: {}", id);
        return false;
    }
}