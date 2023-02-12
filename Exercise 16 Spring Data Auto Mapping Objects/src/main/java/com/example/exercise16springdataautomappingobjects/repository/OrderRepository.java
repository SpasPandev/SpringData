package com.example.exercise16springdataautomappingobjects.repository;

import com.example.exercise16springdataautomappingobjects.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
