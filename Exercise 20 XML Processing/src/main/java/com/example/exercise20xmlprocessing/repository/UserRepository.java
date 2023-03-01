package com.example.exercise20xmlprocessing.repository;

import com.example.exercise20xmlprocessing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllBySoldProductsIsNotNullOrderByLastNameAscFirstName();

    List<User> findAllBySoldProductsIsNotNullOrderBySoldProductsDescLastName();
}
