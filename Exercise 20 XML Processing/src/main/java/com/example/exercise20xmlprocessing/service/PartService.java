package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.entity.Part;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Set;

public interface PartService {

    void seedParts(String filePath) throws JAXBException, FileNotFoundException;

    Set<Part> getRandomParts();
}
