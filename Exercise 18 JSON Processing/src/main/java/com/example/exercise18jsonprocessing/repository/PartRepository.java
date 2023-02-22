package com.example.exercise18jsonprocessing.repository;

import com.example.exercise18jsonprocessing.model.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
