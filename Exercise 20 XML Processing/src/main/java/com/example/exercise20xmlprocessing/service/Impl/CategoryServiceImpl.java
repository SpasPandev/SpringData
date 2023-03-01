package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.CategorySeedRootDto;
import com.example.exercise20xmlprocessing.model.dto.CategoryWithProductsInfoDto;
import com.example.exercise20xmlprocessing.model.dto.CategoryWithProductsInfoRootDto;
import com.example.exercise20xmlprocessing.model.entity.Category;
import com.example.exercise20xmlprocessing.repository.CategoryRepository;
import com.example.exercise20xmlprocessing.service.CategoryService;
import com.example.exercise20xmlprocessing.util.Impl.XmlParserImpl;
import com.example.exercise20xmlprocessing.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParserImpl xmlParser;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParserImpl xmlParser) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCategories(String filePath) throws JAXBException, FileNotFoundException {

        if (categoryRepository.count() > 0) {
            return;
        }

        CategorySeedRootDto categorySeedRootDto = xmlParser.fromFile(filePath, CategorySeedRootDto.class);

        categorySeedRootDto.getCategories()
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> getRandomCategories() {

        Set<Category> randomCategories = new HashSet<>();

        int number = ThreadLocalRandom.current().nextInt(1, 3);

        long categoriesCount = categoryRepository.count();

        for (int i = 0; i < number; i++) {

            long randomId = ThreadLocalRandom.current().nextLong(1, categoriesCount + 1);

            randomCategories.add(categoryRepository.findById(randomId).orElse(null));
        }

        return randomCategories;
    }

    @Override
    public CategoryWithProductsInfoRootDto findAllOrderByNumberOfProductsInCategory() {

        List<Category> categories = categoryRepository.findAllByOrderByProducts();

        CategoryWithProductsInfoRootDto categoryWithProductsInfoRootDto = new CategoryWithProductsInfoRootDto();

        categoryWithProductsInfoRootDto.setCategories(categories
                .stream()
                .map(category -> {
                    CategoryWithProductsInfoDto c = modelMapper.map(category, CategoryWithProductsInfoDto.class);

                    c.setProductsCount(category.getProducts().size());
                    c.setAveragePrice(BigDecimal.valueOf(category
                            .getProducts()
                            .stream()
                            .mapToDouble(product -> product.getPrice().doubleValue())
                            .average().orElse(0)));
                    c.setTotalRevenue(BigDecimal.valueOf(category
                            .getProducts()
                            .stream()
                            .mapToDouble(product -> product.getPrice().doubleValue())
                            .sum()));

                    return c;
                })
                .collect(Collectors.toList()));

        return categoryWithProductsInfoRootDto;
    }
}
