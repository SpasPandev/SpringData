package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.LocalSuppliersDto;
import com.example.exercise20xmlprocessing.model.dto.LocalSuppliersRootDto;
import com.example.exercise20xmlprocessing.model.dto.SupplierSeedDto;
import com.example.exercise20xmlprocessing.model.dto.SupplierSeedRootDto;
import com.example.exercise20xmlprocessing.model.entity.Supplier;
import com.example.exercise20xmlprocessing.repository.SupplierRepository;
import com.example.exercise20xmlprocessing.service.SupplierService;
import com.example.exercise20xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSuppliers(String filePath) throws JAXBException, FileNotFoundException {

        if (supplierRepository.count() > 0) {
            return;
        }

        SupplierSeedRootDto supplierSeedRootDto = xmlParser.fromFile(filePath, SupplierSeedRootDto.class);

        supplierSeedRootDto.getSuppliers()
                .stream()
                .map(supplierSeedDto -> modelMapper.map(supplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);
    }

    @Override
    public Supplier getRandomSupplier() {

        long randomId = ThreadLocalRandom.current().nextLong(1, supplierRepository.count() + 1);

        return supplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public LocalSuppliersRootDto findAllNotImporters() {

        List<Supplier> suppliers = supplierRepository.findAllByImporterFalse();

        LocalSuppliersRootDto localSuppliersRootDto = new LocalSuppliersRootDto();

        localSuppliersRootDto.setSuppliers(suppliers
                .stream()
                .map(supplier -> {
                    LocalSuppliersDto dto = modelMapper.map(supplier, LocalSuppliersDto.class);
                    dto.setPartsCount(supplier.getParts().size());

                    return dto;
                })
                .collect(Collectors.toList()));

        return localSuppliersRootDto;
    }
}
