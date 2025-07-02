package com.example.hospital_crud.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ward {

    @Id
    @Column(name = "number_ward", unique = true, nullable = false)
    @Min(value = 1, message = "Номер палаты должен быть больше 0")
    private Integer numberWard;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL)
    private List<Patient> patients;
}
