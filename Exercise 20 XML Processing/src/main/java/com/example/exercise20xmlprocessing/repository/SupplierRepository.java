package com.example.exercise20xmlprocessing.repository;

import com.example.exercise20xmlprocessing.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findAllByImporterFalse();
}
