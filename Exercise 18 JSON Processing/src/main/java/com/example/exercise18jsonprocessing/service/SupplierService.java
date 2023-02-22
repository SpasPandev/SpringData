package com.example.exercise18jsonprocessing.service;

import com.example.exercise18jsonprocessing.model.dto.LocalSuppliersInfoDto;
import com.example.exercise18jsonprocessing.model.entity.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    void seedSuppliers() throws IOException;

    Supplier getRandomSupplier();

    List<LocalSuppliersInfoDto> findAllNotIporters();
}
