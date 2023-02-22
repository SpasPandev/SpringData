package com.example.exercise18jsonprocessing.repository;

import com.example.exercise18jsonprocessing.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c order by c.birthDate, c.youngDriver")
    List<Customer> findAllOrderedByBirthDateAndYoungDriver();

    @Query("SELECT c FROM Customer c " +
            "WHERE (SELECT COUNT(s) FROM Sale s WHERE s.customer.id = c.id) > 0")
    List<Customer> findAllWithAtLeastOneCarBought();
}
