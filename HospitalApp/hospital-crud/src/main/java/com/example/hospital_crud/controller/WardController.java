package com.example.hospital_crud.controller;

import com.example.hospital_crud.model.Ward;
import com.example.hospital_crud.repository.WardRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.hospital_crud.exception.*;

import java.util.List;
//import java.util.Optional;
//import java.util.UUID;

//@RestController
//@RequestMapping("/api/wards")
public class WardController {
    private final WardRepository wardRepository;

    //@Autowired
    public WardController(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    @GetMapping
    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    @GetMapping("/{id}")
public ResponseEntity<Ward> getWardById(@PathVariable Integer id) {
    Ward ward = wardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ward not found"));
    return ResponseEntity.ok(ward);
}

    @PostMapping
    public Ward createWard(@RequestBody Ward ward) {
        return wardRepository.save(ward);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ward> updateWard(@PathVariable Integer id, @RequestBody Ward wardDetails) {
        return wardRepository.findById(id).map(ward -> {
            ward.setNumberWard(wardDetails.getNumberWard());
            ward.setDoctor(wardDetails.getDoctor());
            Ward updated = wardRepository.save(ward);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWard(@PathVariable Integer id)  {
        return wardRepository.findById(id)
                .map(ward -> {
                    wardRepository.delete(ward);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
