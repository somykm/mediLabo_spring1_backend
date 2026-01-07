package com.abernathyclinic.medilabo_demographics.repository;

import com.abernathyclinic.medilabo_demographics.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByFirstNameAndLastName(String firstName, String lastName);
}