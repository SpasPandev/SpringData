package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.dto.LocalSuppliersRootDto;
import com.example.exercise20xmlprocessing.model.entity.Supplier;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SupplierService {

    void seedSuppliers(String filePath) throws JAXBException, FileNotFoundException;

    Supplier getRandomSupplier();

    LocalSuppliersRootDto findAllNotImporters();
}
