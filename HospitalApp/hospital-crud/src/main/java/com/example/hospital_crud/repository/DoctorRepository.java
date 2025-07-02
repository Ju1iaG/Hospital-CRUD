package com.example.hospital_crud.repository;

import com.example.hospital_crud.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID>{

}
