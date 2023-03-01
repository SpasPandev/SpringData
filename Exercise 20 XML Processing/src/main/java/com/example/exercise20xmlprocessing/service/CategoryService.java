package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.dto.CategoryWithProductsInfoRootDto;
import com.example.exercise20xmlprocessing.model.entity.Category;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Set;

public interface CategoryService {

    void seedCategories(String filePath) throws JAXBException, FileNotFoundException;

    Set<Category> getRandomCategories();

    CategoryWithProductsInfoRootDto findAllOrderByNumberOfProductsInCategory();
}
