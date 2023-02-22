package com.example.exercise18jsonprocessing;

import com.example.exercise18jsonprocessing.model.dto.*;
import com.example.exercise18jsonprocessing.service.*;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.OUTPUT_DIRECTORY_PATH;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String PRODUCTS_IN_RANGE = "products-in-range.json";
    private static final String SUCCESSFULL_SOLD_PRODUCTS = "successfull-sold-products.json";
    private static final String CATEGORIES_BY_PRODUCTS_COUNT = "categories-by-products-count.json";
    private static final String USERS_AND_PRODUCTS = "users-and-products.json";
    private static final String ORDERED_CUSTOMERS = "ordered-customers.json";
    private static final String TOYOTA_CARS = "toyota-cars.json";
    private static final String LOCAL_SUPPLIERS = "local-suppliers.json";
    private static final String CARS_AND_PARTS = "cars-and-parts.json";
    private static final String CUSTOMERS_TOTAL_SALES = "customers-total-sales.json";
    private static final String SALES_DISCOUNTS = "sales-discounts.json";
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    public CommandLineRunnerImpl(UserService userService, ProductService productService, CategoryService categoryService, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, Gson gson) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.gson = gson;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();

        while (true) {
            System.out.println("Please enter exercise number: ");
            int exNum = Integer.parseInt(bufferedReader.readLine());

            switch (exNum) {

                case 1 -> findAllProductsInRangeWithNoBuyer();
                case 2 -> successfullySoldProducts();
                case 3 -> categoriesByProductsCount();
                case 4 -> usersAndProducts();
                case 5 -> orderedCustomers();
                case 6 -> carsFromMakeToyota();
                case 7 -> localSuppliers();
                case 8 -> carsWithTheirListOfParts();
                case 9 -> totalSalesByCustomer();
                case 10 -> salesWithAppliedDiscounts();
            }

            System.out.println("OK");
        }
    }

    private void salesWithAppliedDiscounts() throws IOException {

        List<SalesInfoAboutCarDto> salesInfoAboutCarDtos = saleService.findAllSales();

        String content = gson.toJson(salesInfoAboutCarDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + SALES_DISCOUNTS, content);
    }

    private void totalSalesByCustomer() throws IOException {

        List<CustomerWithTotalSalesDto> customerWithTotalSalesDtos =
                customerService.getAllCustomersWithBoughtCars();

        String content = gson.toJson(customerWithTotalSalesDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + CUSTOMERS_TOTAL_SALES, content);
    }

    private void carsWithTheirListOfParts() throws IOException {

        List<CarsAndPartsDto> carsAndPartsDtos = carService.findAllCarsWithTheirParts();

        String content = gson.toJson(carsAndPartsDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + CARS_AND_PARTS, content);
    }

    private void localSuppliers() throws IOException {

        List<LocalSuppliersInfoDto> localSuppliersInfoDtos =
                supplierService.findAllNotIporters();

        String content = gson.toJson(localSuppliersInfoDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + LOCAL_SUPPLIERS, content);
    }

    private void carsFromMakeToyota() throws IOException {

        List<CarsFromMakeInfoDto> carsFromMakeInfoDtos =
                carService.findAllCarsFromMakeToyotaAndOrderThemByModelThenTravelledDistanceDesc();

        String content = gson.toJson(carsFromMakeInfoDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + TOYOTA_CARS, content);
    }

    private void orderedCustomers() throws IOException {

        List<OrderedCustomersDto> orderedCustomersDto =
                customerService.getAllCustomersOrderedByBirthday();

        String content = gson.toJson(orderedCustomersDto);

        writeToFile(OUTPUT_DIRECTORY_PATH + ORDERED_CUSTOMERS, content);
    }

    private void usersAndProducts() throws IOException {
        UsersAndProductsDto usersAndProductsDtos =
                userService.findAllWithSoldProductsOrderThemBySoldProductsDesc();

        String content = gson.toJson(usersAndProductsDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + USERS_AND_PRODUCTS, content);
    }

    private void categoriesByProductsCount() throws IOException {

        List<CategoriesByProductsCountDto> categoriesByProductsCountDtos =
                categoryService.getAllCategoriesByProducts();

        String content = gson.toJson(categoriesByProductsCountDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + CATEGORIES_BY_PRODUCTS_COUNT, content);
    }

    private void successfullySoldProducts() throws IOException {

        List<UserSoldProductsDto> userSoldProductsDtos =
                userService.findAllSuccessfullySoldProducts();

        String content = gson.toJson(userSoldProductsDtos);

        writeToFile(OUTPUT_DIRECTORY_PATH + SUCCESSFULL_SOLD_PRODUCTS, content);
    }

    private void findAllProductsInRangeWithNoBuyer() throws IOException {

        List<ProductNameAndPriceDto> productNameAndPriceDtoList =
                productService.findProductsInRange(new BigDecimal(500), new BigDecimal(1000));


        String content = gson.toJson(productNameAndPriceDtoList);

        writeToFile(OUTPUT_DIRECTORY_PATH + PRODUCTS_IN_RANGE, content);
    }

    private void writeToFile(String path, String content) throws IOException {

        Files.write(Path.of(path), Collections.singleton(content));
    }

    private void seedData() throws IOException {

        userService.seedUsers();
        categoryService.seedCategories();
        productService.seedProducts();
        supplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();
        customerService.seedCustomer();
        saleService.seedSale();
    }
}
