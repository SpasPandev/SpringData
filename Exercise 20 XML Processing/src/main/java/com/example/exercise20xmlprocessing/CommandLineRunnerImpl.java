package com.example.exercise20xmlprocessing;

import com.example.exercise20xmlprocessing.model.dto.*;
import com.example.exercise20xmlprocessing.service.*;
import com.example.exercise20xmlprocessing.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final static String RESOURCES_FILES_DIRECTORY = "src/main/resources/files/";
    private final static String USERS_FILE_NAME = "users.xml";
    private final static String PRODUCTS_FILE_NAME = "products.xml";
    private final static String CATEGORIES_FILE_NAME = "categories.xml";
    private final static String OUTPUT_FILE_DIRECTORY = "src/main/resources/files/out/";
    private final static String PRODUCTS_IN_RANGE = "products-in-range.xml";
    private final static String USERS_SOLD_PRODUCTS = "users-sold-products.xml";
    private final static String CATEGORIES_BY_PRODUCTS = "categories-by-products.xml";
    private final static String USERS_AND_PRODUCTS = "users-and-products.xml";
    private final static String SUPPLIERS_FILE_NAME = "suppliers.xml";
    private final static String PARTS_FILE_NAME = "parts.xml";
    private final static String CARS_FILE_NAME = "cars.xml";
    private final static String CUSTOMERS_FILE_NAME = "customers.xml";
    private final static String ORDERED_CUSTOMERS = "ordered-customers.xml";
    private final static String TOYOTA_CARS = "toyota-cars.xml";
    private final static String LOCAL_SUPPLIERS = "local-suppliers.xml";
    private final static String CARS_AND_PARTS = "cars-and-parts.xml";
    private final static String CUSTOMERS_TOTAL_SALES = "customers-total-sales.xml";
    private final static String SALES_DISCOUNTS = "sales-discounts.xml";
    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final XmlParser xmlParser;

    public CommandLineRunnerImpl(UserService userService, ProductService productService, CategoryService categoryService, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, XmlParser xmlParser) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.xmlParser = xmlParser;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();

        while (true) {
            System.out.println("Please enter number of exercise or 0 to finish: ");
            int exNum = Integer.parseInt(bufferedReader.readLine());

            if (exNum == 0){
                System.out.println("End of program!");
                break;
            }

            switch (exNum) {

                case 1 -> productsInRange();
                case 2 -> successfullySoldProducts();
                case 3 -> categoriesByProductsCount();
                case 4 -> usersAndProducts();
                case 5 -> orderedCustomers();
                case 6 -> carsFromMakeToyota();
                case 7 -> localSuppliers();
                case 8 -> carsWithTheirListOfParts();
                case 9 -> totalSalesByCustomer();
                case 10 -> salesWithAppliedDiscount();
            }
        }
    }

    private void salesWithAppliedDiscount() throws JAXBException {

        SalesInfoRootDto salesInfoRootDto = saleService.findAllSales();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + SALES_DISCOUNTS, salesInfoRootDto);

        System.out.println("Ok");
    }

    private void totalSalesByCustomer() throws JAXBException {

        TotalSalesByCustomerRootDto totalSalesByCustomerRootDto = customerService.findAllCustomerWithAtLeastOneCar();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + CUSTOMERS_TOTAL_SALES, totalSalesByCustomerRootDto);

        System.out.println("Ok");
    }

    private void carsWithTheirListOfParts() throws JAXBException {

        CarsWithTheirListOfPartsRootDto carsWithTheirListOfPartsRootDto = carService.findAllCarsWithTheirParts();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + CARS_AND_PARTS, carsWithTheirListOfPartsRootDto);

        System.out.println("Ok");
    }

    private void localSuppliers() throws JAXBException {

        LocalSuppliersRootDto localSuppliersRootDto = supplierService.findAllNotImporters();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + LOCAL_SUPPLIERS, localSuppliersRootDto);

        System.out.println("Ok");
    }

    private void carsFromMakeToyota() throws JAXBException {

        ToyotaCarsRootDto toyotaCarsRootDto = carService.findAllToyotaCarsOrderByModelThenTravelledDistanceDesc();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + TOYOTA_CARS, toyotaCarsRootDto);

        System.out.println("Ok");
    }

    private void orderedCustomers() throws JAXBException {

        OrderedCustomersRootDto orderedCustomersRootDto = customerService.findAllOrderedByBirthDate();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + ORDERED_CUSTOMERS, orderedCustomersRootDto);

        System.out.println("Ok");
    }

    private void usersAndProducts() throws JAXBException {

        UsersAndProductsRootDto usersAndProductsRootDto = userService.findAllWithSoldProducts();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + USERS_AND_PRODUCTS, usersAndProductsRootDto);

        System.out.println("Ok");
    }

    private void categoriesByProductsCount() throws JAXBException {

        CategoryWithProductsInfoRootDto categoryWithProductsInfoRootDto =
                categoryService.findAllOrderByNumberOfProductsInCategory();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + CATEGORIES_BY_PRODUCTS, categoryWithProductsInfoRootDto);

        System.out.println("Ok");
    }

    private void successfullySoldProducts() throws JAXBException {

        UserSoldProductsRootDto userSoldProductsRootDto = userService.findAllWithSoldProductsAndBuyer();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + USERS_SOLD_PRODUCTS, userSoldProductsRootDto);

        System.out.println("Ok");
    }

    private void productsInRange() throws JAXBException {

        ProductViewRootDto productViewRootDto = productService.getAllProductsInRangeWithNoBuyer();

        xmlParser.writeToFile(OUTPUT_FILE_DIRECTORY + PRODUCTS_IN_RANGE, productViewRootDto);

        System.out.println("Ok");
    }

    private void seedData() throws JAXBException, FileNotFoundException {

        userService.seedUsers(RESOURCES_FILES_DIRECTORY + USERS_FILE_NAME);
        categoryService.seedCategories(RESOURCES_FILES_DIRECTORY + CATEGORIES_FILE_NAME);
        productService.seedProducts(RESOURCES_FILES_DIRECTORY + PRODUCTS_FILE_NAME);
        supplierService.seedSuppliers(RESOURCES_FILES_DIRECTORY + SUPPLIERS_FILE_NAME);
        partService.seedParts(RESOURCES_FILES_DIRECTORY + PARTS_FILE_NAME);
        carService.seedCars(RESOURCES_FILES_DIRECTORY + CARS_FILE_NAME);
        customerService.seedCustomers(RESOURCES_FILES_DIRECTORY + CUSTOMERS_FILE_NAME);
        saleService.seedSales();
    }
}
