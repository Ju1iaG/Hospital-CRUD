package com.example.hospital_crud.controller;

import com.example.hospital_crud.model.Doctor;
import com.example.hospital_crud.repository.DoctorRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequestMapping("/doctors")
public class DoctorPageController {
    private final DoctorRepository doctorRepository;

    public DoctorPageController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Все доктора
    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors/list"; // templates/doctors/list.html
    }

    // Один доктор
    @GetMapping("/{id}")
    public String getDoctor(@PathVariable UUID id, Model model) {
    Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid doctor Id: " + id));
    model.addAttribute("doctor", doctor);
    return "doctors/detail"; // templates/doctors/detail.html
    }

    // Форма создания
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/new"; // templates/doctors/new.html
    }

    // Обработка формы создания
    @PostMapping
    public String createDoctor(@ModelAttribute @Valid Doctor doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        // Если есть ошибки, верни форму с ошибками
            return "doctors/new";
        }
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    // Показать форму редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor Id: " + id));
        model.addAttribute("doctor", doctor);
        return "doctors/edit"; // твой edit.html для доктора
    }

    // Сохранить изменения
    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable("id") UUID id, @ModelAttribute Doctor doctor) {
        doctor.setId(id);
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    // Удаление
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") UUID id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }
}
