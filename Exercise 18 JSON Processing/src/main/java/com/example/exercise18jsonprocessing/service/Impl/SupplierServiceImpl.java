package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.LocalSuppliersInfoDto;
import com.example.exercise18jsonprocessing.model.dto.SupplierSeedDto;
import com.example.exercise18jsonprocessing.model.entity.Supplier;
import com.example.exercise18jsonprocessing.repository.SupplierRepository;
import com.example.exercise18jsonprocessing.service.SupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIERS_FILE_NAME = "suppliers.json";
    private final SupplierRepository supplierRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, Gson gson, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSuppliers() throws IOException {

        if (supplierRepository.count() > 0) {

            return;
        }

        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCES_FILE_PATH + SUPPLIERS_FILE_NAME)),
                SupplierSeedDto[].class))
                .map(supplierSeedDto -> modelMapper.map(supplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);
    }

    @Override
    public Supplier getRandomSupplier() {

        long randomId = ThreadLocalRandom.current().nextLong(1, supplierRepository.count() + 1);

        return supplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<LocalSuppliersInfoDto> findAllNotIporters() {

        return supplierRepository.findAllByImporterFalse()
                .stream()
                .map(supplier -> {
                    LocalSuppliersInfoDto localSuppliersInfoDto = modelMapper.map(supplier, LocalSuppliersInfoDto.class);
                    localSuppliersInfoDto.setPartsCount(supplier.getParts().size());

                    return localSuppliersInfoDto;
                })
                .collect(Collectors.toList());
    }
}
