package com.example.hospital_crud.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient", uniqueConstraints = {
    @UniqueConstraint(columnNames = "policy_number")
})
public class Patient {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "ФИО не должно быть пустым")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z\\s\\-]+$", message = "ФИО должно содержать только буквы")
    @Column(name = "fio_patient", length = 100, nullable = false)
    private String fioPatient;

    @Column(name = "passport_id", length = 11, nullable = false)
    private String passportId;

    @Column(name = "policy_number", length = 16, nullable = false, unique = true)
    private String policyNumber;

    @Column(name = "date_of_birth", nullable = false)
    private java.time.LocalDate dateOfBirth;

    @Column(name = "residential_address", length = 100, nullable = false)
    private String residentialAddress;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "number_ward", nullable = false,referencedColumnName = "number_ward")
    private Ward ward;
}
