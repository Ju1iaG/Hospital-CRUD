package com.example.hospital_crud.repository;

import com.example.hospital_crud.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

}
