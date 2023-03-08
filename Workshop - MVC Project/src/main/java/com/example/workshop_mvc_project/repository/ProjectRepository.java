package com.example.workshop_mvc_project.repository;

import com.example.workshop_mvc_project.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsAllBy();

    Project findByName(String name);

    List<Project> findAllByFinishedIsTrue();
}
