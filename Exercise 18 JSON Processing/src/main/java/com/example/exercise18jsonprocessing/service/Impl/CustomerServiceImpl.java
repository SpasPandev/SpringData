package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.CustomerSeedDto;
import com.example.exercise18jsonprocessing.model.dto.CustomerWithTotalSalesDto;
import com.example.exercise18jsonprocessing.model.dto.OrderedCustomersDto;
import com.example.exercise18jsonprocessing.model.entity.Car;
import com.example.exercise18jsonprocessing.model.entity.Customer;
import com.example.exercise18jsonprocessing.model.entity.Part;
import com.example.exercise18jsonprocessing.model.entity.Sale;
import com.example.exercise18jsonprocessing.repository.CarRepository;
import com.example.exercise18jsonprocessing.repository.CustomerRepository;
import com.example.exercise18jsonprocessing.service.CustomerService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_FILE_NAME = "customers.json";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public void seedCustomer() throws IOException {

        if (customerRepository.count() > 0) {
            return;
        }

        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCES_FILE_PATH + CUSTOMER_FILE_NAME)),
                        CustomerSeedDto[].class))
                .map(customerSeedDto -> modelMapper.map(customerSeedDto, Customer.class))
                .forEach(customerRepository::save);
    }

    @Override
    public Customer getRandomCustomer() {


        long randomId = ThreadLocalRandom.current().nextLong(1, customerRepository.count() + 1);

        return customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<OrderedCustomersDto> getAllCustomersOrderedByBirthday() {

        return customerRepository.findAllOrderedByBirthDateAndYoungDriver()
                .stream()
                .map(customer -> modelMapper.map(customer, OrderedCustomersDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerWithTotalSalesDto> getAllCustomersWithBoughtCars() {

        List<Customer> customers = customerRepository.findAllWithAtLeastOneCarBought();

        List<CustomerWithTotalSalesDto> customerWithTotalSalesDtos = customers.stream()
                .map(customer -> {
                    CustomerWithTotalSalesDto mappedCustomer = modelMapper.map(customer, CustomerWithTotalSalesDto.class);

                    mappedCustomer.setBoughtCars(customer.getSales().size());

                    List<Car> cars = customer.getSales()
                            .stream().map(Sale::getCar).toList();

                    double totalSum = cars.stream()
                            .mapToDouble(car -> {
                                return  car.getParts().stream()
                                .mapToDouble(part -> Double.parseDouble(String.valueOf(part.getPrice())))
                                .sum();
                    })
                            .sum();

                    mappedCustomer.setSpentMoney(BigDecimal.valueOf(totalSum));

                    return mappedCustomer;
                }).collect(Collectors.toList());


        customerWithTotalSalesDtos.sort(Comparator.comparing(CustomerWithTotalSalesDto::getSpentMoney).reversed()
                .thenComparing(CustomerWithTotalSalesDto::getBoughtCars));

        return customerWithTotalSalesDtos;
    }
}
