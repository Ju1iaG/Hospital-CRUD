package com.example.hospital_crud.controller;

import com.example.hospital_crud.model.Patient;
import com.example.hospital_crud.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientRepository patientRepository;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable UUID id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patient createPatient(@Valid @RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable UUID id,
            @Valid @RequestBody Patient updatedPatient) {

        return patientRepository.findById(id)
                .map(existing -> {
                    updatedPatient.setId(id);
                    return ResponseEntity.ok(patientRepository.save(updatedPatient));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        return patientRepository.findById(id)
                .map(existing -> {
                    patientRepository.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
