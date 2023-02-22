package com.example.exercise18jsonprocessing.service;

import com.example.exercise18jsonprocessing.model.dto.CategoriesByProductsCountDto;
import com.example.exercise18jsonprocessing.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

    List<CategoriesByProductsCountDto> getAllCategoriesByProducts();
}
