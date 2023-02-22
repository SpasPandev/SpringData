package com.example.exercise18jsonprocessing.service;

import com.example.exercise18jsonprocessing.model.entity.Part;

import java.io.IOException;
import java.util.Set;

public interface PartService {

    void seedParts() throws IOException;

    Set<Part> getRandomParts();
}
