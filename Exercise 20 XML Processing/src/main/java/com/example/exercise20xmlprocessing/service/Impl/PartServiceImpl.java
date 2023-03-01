package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.PartSeedRootDto;
import com.example.exercise20xmlprocessing.model.entity.Part;
import com.example.exercise20xmlprocessing.repository.PartRepository;
import com.example.exercise20xmlprocessing.service.PartService;
import com.example.exercise20xmlprocessing.service.SupplierService;
import com.example.exercise20xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final SupplierService supplierService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public PartServiceImpl(PartRepository partRepository, SupplierService supplierService, ModelMapper modelMapper, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedParts(String filePath) throws JAXBException, FileNotFoundException {

        if (partRepository.count() > 0) {
            return;
        }

        PartSeedRootDto partSeedRootDto = xmlParser.fromFile(filePath, PartSeedRootDto.class);

        partSeedRootDto.getParts()
                .stream()
                .map(partSeedDto -> {
                    Part part = modelMapper.map(partSeedDto, Part.class);

                    part.setSupplier(supplierService.getRandomSupplier());

                    return part;
                })
                .forEach(partRepository::save);

    }

    @Override
    public Set<Part> getRandomParts() {

        int partsCount = ThreadLocalRandom.current().nextInt(10, 21);

        Set<Part> parts = new HashSet<>();

        long partsRepoCount = partRepository.count();

        for (int i = 0; i < partsCount; i++) {

            parts.add(partRepository.findById(
                    ThreadLocalRandom.current().nextLong(1, partsRepoCount + 1)).orElse(null));
        }
        return parts;
    }
}
