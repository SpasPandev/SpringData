package com.example.lab11springdataintro.repositories;

import com.example.lab11springdataintro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void findUserById(Long id);
}
