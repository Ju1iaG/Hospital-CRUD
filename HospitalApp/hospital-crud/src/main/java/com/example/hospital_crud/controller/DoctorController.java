package com.example.hospital_crud.controller;

import com.example.hospital_crud.model.Doctor;
import com.example.hospital_crud.repository.DoctorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorRepository doctorRepository;

    //@Autowired
    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable UUID id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctorDetails) {
        return doctorRepository.findById(id).map(doctor -> {
            doctor.setFioDoctor(doctorDetails.getFioDoctor());
            doctor.setJobTitle(doctorDetails.getJobTitle());
            doctor.setPassportId(doctorDetails.getPassportId());
            doctor.setDateOfBirth(doctorDetails.getDateOfBirth());
            Doctor updated = doctorRepository.save(doctor);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
    return doctorRepository.findById(id)
        .map(doctor -> {
            doctorRepository.delete(doctor);
            return ResponseEntity.noContent().<Void>build(); 
        })
        .orElse(ResponseEntity.notFound().build());
}
}
