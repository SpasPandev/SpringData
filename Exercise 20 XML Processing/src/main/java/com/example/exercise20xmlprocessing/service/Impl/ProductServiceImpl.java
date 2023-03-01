package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.ProductNamePriceAndSellerDto;
import com.example.exercise20xmlprocessing.model.dto.ProductSeedRootDto;
import com.example.exercise20xmlprocessing.model.dto.ProductViewRootDto;
import com.example.exercise20xmlprocessing.model.entity.Product;
import com.example.exercise20xmlprocessing.repository.ProductRepository;
import com.example.exercise20xmlprocessing.service.CategoryService;
import com.example.exercise20xmlprocessing.service.ProductService;
import com.example.exercise20xmlprocessing.service.UserService;
import com.example.exercise20xmlprocessing.util.ValidationUtil;
import com.example.exercise20xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts(String filePath) throws JAXBException, FileNotFoundException {

        if (productRepository.count() > 0) {
            return;
        }

        ProductSeedRootDto productSeedRootDto = xmlParser.fromFile(filePath, ProductSeedRootDto.class);

        productSeedRootDto.getProducts()
                .stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.getRandomUser());
                    product.setCategories(categoryService.getRandomCategories());

                    if (product.getPrice().compareTo(new BigDecimal(700)) > 0) {

                        product.setBuyer(userService.getRandomUser());
                    }
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductViewRootDto getAllProductsInRangeWithNoBuyer() {

        List<Product> products = productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(new BigDecimal(500), new BigDecimal(1000));

        ProductViewRootDto productViewRootDto = new ProductViewRootDto();

        productViewRootDto.setProducts(products
                .stream()
                .map(product -> {
                    ProductNamePriceAndSellerDto p = modelMapper.map(product, ProductNamePriceAndSellerDto.class);

                    p.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));

                    return p;
                })
                .collect(Collectors.toList()));

        return productViewRootDto;
    }
}
