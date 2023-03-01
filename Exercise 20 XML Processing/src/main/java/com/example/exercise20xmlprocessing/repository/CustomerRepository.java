package com.example.exercise20xmlprocessing.repository;

import com.example.exercise20xmlprocessing.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c order by c.birthDate, c.youngDriver")
    List<Customer> findAllOrderedByBirthDateAndYoungDriver();

    List<Customer> findAllBySalesIsNotNull();
}
