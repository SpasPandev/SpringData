package com.example.workshop_mvc_project.repository;

import com.example.workshop_mvc_project.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsAllBy();

    Company findByName(String name);
}
