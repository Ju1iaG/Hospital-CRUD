package com.example.hospital_crud.controller;

import com.example.hospital_crud.model.Doctor;
import com.example.hospital_crud.model.Ward;
import com.example.hospital_crud.repository.WardRepository;

import jakarta.validation.Valid;

import com.example.hospital_crud.repository.DoctorRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/wards")
public class WardPageController {
    private final WardRepository wardRepository;
    private final DoctorRepository doctorRepository;

    public WardPageController(WardRepository wardRepository, DoctorRepository doctorRepository) {
        this.wardRepository = wardRepository;
        this.doctorRepository = doctorRepository;
    }

    // Показать все палаты
    @GetMapping
    public String listWards(Model model) {
        model.addAttribute("wards", wardRepository.findAll());
        return "wards/list"; // templates/wards/list.html
    }

    // Показать одну палату по ID
    @GetMapping("/{id}")
    public String getWard(@PathVariable Integer id, Model model) {
        Ward ward = wardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ward Id:" + id));
        model.addAttribute("ward", ward);
        return "wards/detail"; // templates/wards/detail.html
    }

    // Форма для создания новой палаты
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ward", new Ward());
        model.addAttribute("doctors", doctorRepository.findAll()); // Для выбора доктора
        return "wards/new"; // templates/wards/new.html
    }

    // Обработка отправки формы создания палаты
    @PostMapping
    public String createWard(@ModelAttribute @Valid Ward ward, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        // Если есть ошибки, верни форму с ошибками
            return "wards/new";
        }
        wardRepository.save(ward);
        return "redirect:/wards";
    }

    // @GetMapping("/{numberWard}")
    // public String getWardByNumber(@PathVariable Integer numberWard, Model model) {
    // Ward ward = wardRepository.findByNumberWard(numberWard)
    //                .orElseThrow(() -> new IllegalArgumentException("Invalid ward Number:" + numberWard));
    // model.addAttribute("ward", ward);
    // return "ward_detail";
    // }

    @GetMapping("/delete/{id}")
    public String deleteWard(@PathVariable("id") Integer id) {
    wardRepository.deleteById(id);
    return "redirect:/wards";
    }

    @GetMapping("/edit/{id}")
    public String editWardForm(@PathVariable Integer id, Model model) {
        Ward ward = wardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ward Id:" + id));
        model.addAttribute("ward", ward);
        model.addAttribute("doctors", doctorRepository.findAll());
        return "wards/edit";
    }

    @PostMapping("/update/{id}")
    public String updateWard(@PathVariable Integer id, @ModelAttribute Ward ward) {
    // 1. Найти палату по ID (PK = numberWard)
    Ward existingWard = wardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ward Id: " + id));

    // 2. Найти врача по ID из формы
    Doctor doctor = doctorRepository.findById(ward.getDoctor().getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid doctor Id: " + ward.getDoctor().getId()));

    // 3. Обновить только связанные поля
    existingWard.setDoctor(doctor);

    // 4. Сохранить обновлённую палату
    wardRepository.save(existingWard);

    return "redirect:/wards";
    }

}
