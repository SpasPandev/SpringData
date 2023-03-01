package com.example.exercise20xmlprocessing.repository;

import com.example.exercise20xmlprocessing.model.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

}
