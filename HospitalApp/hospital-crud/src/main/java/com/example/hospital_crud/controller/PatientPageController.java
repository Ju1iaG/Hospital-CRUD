package com.example.hospital_crud.controller;

import com.example.hospital_crud.exception.ResourceNotFoundException;
import com.example.hospital_crud.model.Patient;
import com.example.hospital_crud.model.Ward;
import com.example.hospital_crud.repository.PatientRepository;
import com.example.hospital_crud.repository.WardRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

// PatientPageController.java
@Controller
@RequestMapping("/patients")
public class PatientPageController {

    private final PatientRepository patientRepository;
    private final WardRepository wardRepository;

    public PatientPageController(PatientRepository patientRepository, WardRepository wardRepository) {
        this.patientRepository = patientRepository;
        this.wardRepository = wardRepository;
    }

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patients/list";
    }

    @GetMapping("/{id}")
    public String getPatient(@PathVariable UUID id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id: " + id));
        model.addAttribute("patient", patient);
        return "patients/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("wards", wardRepository.findAll());
        return "patients/new";
    }

    @PostMapping
    public String createPatient(@ModelAttribute @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        // Если есть ошибки, верни форму с ошибками
            return "patients/new";
        }
        if (patient.getWard() == null || patient.getWard().getNumberWard() == null) {
            throw new IllegalArgumentException("Ward must be selected");
        }
        Ward ward = wardRepository.findById(patient.getWard().getNumberWard())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Ward Id"));
        patient.setWard(ward);

        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id: " + id));
        model.addAttribute("patient", patient);
        model.addAttribute("wards", wardRepository.findAll());
        return "patients/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable UUID id, @ModelAttribute Patient patient) {
        patient.setId(id);
        if (patient.getWard() == null || patient.getWard().getNumberWard() == null) {
            throw new IllegalArgumentException("Ward must be selected");
        }
        Ward ward = wardRepository.findById(patient.getWard().getNumberWard())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Ward Id"));
        patient.setWard(ward);

        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable UUID id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }
}
