package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.dto.ProductViewRootDto;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface ProductService {

    void seedProducts(String filePath) throws JAXBException, FileNotFoundException;

    ProductViewRootDto getAllProductsInRangeWithNoBuyer();
}
