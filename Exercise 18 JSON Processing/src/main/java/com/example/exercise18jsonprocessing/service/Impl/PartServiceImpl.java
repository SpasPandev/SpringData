package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.PartSeedDto;
import com.example.exercise18jsonprocessing.model.entity.Part;
import com.example.exercise18jsonprocessing.repository.PartRepository;
import com.example.exercise18jsonprocessing.service.PartService;
import com.example.exercise18jsonprocessing.service.SupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class PartServiceImpl implements PartService {

    private final static String PARTS_FILE_NAME = "parts.json";
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts() throws IOException {

        if (partRepository.count() > 0) {
            return;
        }

        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCES_FILE_PATH +
                        PARTS_FILE_NAME)), PartSeedDto[].class))
                .map(partSeedDto -> {
                    Part part = modelMapper.map(partSeedDto, Part.class);
                    part.setSupplier(supplierService.getRandomSupplier());

                    return part;
                })
                .forEach(partRepository::save);
    }

    @Override
    public Set<Part> getRandomParts() {

        int randomNumberOfPartsToGet = ThreadLocalRandom.current().nextInt(3, 5);

        Set<Part> parts = new HashSet<>();

        for (int i = 0; i < randomNumberOfPartsToGet; i++) {

            long randomId = ThreadLocalRandom.current().nextLong(1, partRepository.count() + 1);

            parts.add(partRepository.findById(randomId).orElse(null));
        }

        return parts;
    }
}
