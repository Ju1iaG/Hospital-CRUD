package com.example.hospital_crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "ФИО не должно быть пустым")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z\\s\\-]+$", message = "ФИО должно содержать только буквы")
    private String fioDoctor;

    @NotBlank
    private String jobTitle;

    @NotBlank
    private String passportId;

    @NotNull
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ward> wards = new ArrayList<>();
}
