package com.example.hospital_crud.repository;

import com.example.hospital_crud.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    Optional<Ward> findByNumberWard(Integer numberWard);
    @Query("SELECT w FROM Ward w JOIN FETCH w.doctor")
    List<Ward> findAllWithDoctor();
}
