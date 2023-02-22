package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.CategoriesByProductsCountDto;
import com.example.exercise18jsonprocessing.model.dto.CategorySeedDto;
import com.example.exercise18jsonprocessing.model.entity.Category;
import com.example.exercise18jsonprocessing.repository.CategoryRepository;
import com.example.exercise18jsonprocessing.service.CategoryService;
import com.example.exercise18jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_FILE_NAME = "categories.json";
    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public void seedCategories() throws IOException {

        if (categoryRepository.count() > 0) {
            return;
        }

        String content = Files.readString(Path.of(RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[] categorySeedDtos = gson.fromJson(content, CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> getRandomCategories() {

        Set<Category> randomCategories = new HashSet<>();

        int num = ThreadLocalRandom.current().nextInt(1, 3);

        long categoryCount = categoryRepository.count();

        for (int i = 0; i < num; i++) {

            long randomId = ThreadLocalRandom.current().nextLong(1, categoryCount + 1);

            randomCategories.add(categoryRepository.findById(randomId).orElse(null));
        }

        return randomCategories;
    }

    @Override
    @Transactional
    public List<CategoriesByProductsCountDto> getAllCategoriesByProducts() {

        return categoryRepository.findAllOrderByProductsCount()
                .stream()
                .map(category -> {
                    CategoriesByProductsCountDto categoriesByProductsCountDto =
                            modelMapper.map(category, CategoriesByProductsCountDto.class);

                    categoriesByProductsCountDto
                            .setCategory(category.getName());

                    categoriesByProductsCountDto
                            .setProductsCount(category.getProducts().size());

                    categoriesByProductsCountDto
                            .setAveragePrice(BigDecimal.valueOf(category.getProducts()
                                    .stream()
                                    .mapToDouble(p -> Double.parseDouble(String.valueOf(p.getPrice())))
                                    .average().orElse(0)));

                    categoriesByProductsCountDto
                            .setTotalRevenue(BigDecimal.valueOf(category.getProducts()
                                    .stream()
                                    .mapToDouble(p -> Double.parseDouble(String.valueOf(p.getPrice())))
                                    .sum()));

                    return categoriesByProductsCountDto;
                })
                .collect(Collectors.toList());
    }
}
