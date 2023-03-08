package com.example.workshop_mvc_project.repository;

import com.example.workshop_mvc_project.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndEmail(String username, String email);

    User findByUsernameAndPassword(String username, String password);
}
