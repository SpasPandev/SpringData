package com.example.springintro.service;

import com.example.springintro.model.entity.Category;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

}
