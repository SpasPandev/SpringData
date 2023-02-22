package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.ProductNameAndPriceDto;
import com.example.exercise18jsonprocessing.model.dto.ProductSeedDto;
import com.example.exercise18jsonprocessing.model.entity.Product;
import com.example.exercise18jsonprocessing.repository.ProductRepository;
import com.example.exercise18jsonprocessing.service.CategoryService;
import com.example.exercise18jsonprocessing.service.ProductService;
import com.example.exercise18jsonprocessing.service.UserService;
import com.example.exercise18jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCTS_FILE_NAME = "products.json";
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final Gson gson;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService, Gson gson) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {

        if (productRepository.count() > 0) {

            return;
        }

        String content = Files.readString(Path.of(RESOURCES_FILE_PATH + PRODUCTS_FILE_NAME));

        ProductSeedDto[] productSeedDtos = gson.fromJson(content, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.getRandomUser());

                    if (product.getPrice().compareTo(BigDecimal.valueOf(700)) > 0) {

                        product.setBuyer(userService.getRandomUser());
                    }

                    product.setCategories(categoryService.getRandomCategories());

                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public List<ProductNameAndPriceDto> findProductsInRange(BigDecimal lower, BigDecimal upper) {

        List<Product> products = productRepository.findAllByPriceBetweenAndAndBuyerIsNullOrderByPrice(lower, upper);

        return products
                .stream()
                .map(product -> {
                    ProductNameAndPriceDto productNameAndPriceDto = modelMapper.map(product, ProductNameAndPriceDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(), product.getSeller().getLastName()));

                    return  productNameAndPriceDto;
                })
                .collect(Collectors.toList());
    }
}
