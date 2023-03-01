package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.*;
import com.example.exercise20xmlprocessing.model.entity.Car;
import com.example.exercise20xmlprocessing.model.entity.Customer;
import com.example.exercise20xmlprocessing.model.entity.Sale;
import com.example.exercise20xmlprocessing.repository.CustomerRepository;
import com.example.exercise20xmlprocessing.service.CustomerService;
import com.example.exercise20xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCustomers(String filePath) throws JAXBException, FileNotFoundException {

        if (customerRepository.count() > 0) {
            return;
        }

        CustomerSeedRootDto customerSeedRootDto = xmlParser.fromFile(filePath, CustomerSeedRootDto.class);

        customerSeedRootDto.getCustomers()
                .stream()
                .map(customerSeedDto -> modelMapper.map(customerSeedDto, Customer.class))
                .forEach(customerRepository::save);
    }

    @Override
    public Customer getRandomCustomer() {

        long randomId = ThreadLocalRandom.current().nextLong(1, customerRepository.count() + 1);

        return customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public OrderedCustomersRootDto findAllOrderedByBirthDate() {

        List<Customer> customers = customerRepository.findAllOrderedByBirthDateAndYoungDriver();

        OrderedCustomersRootDto orderedCustomersRootDto = new OrderedCustomersRootDto();

        orderedCustomersRootDto.setCustomers(customers
                .stream()
                .map(customer -> modelMapper.map(customer, OrderedCustomersDto.class))
                .collect(Collectors.toList()));

        return orderedCustomersRootDto;
    }

    @Override
    public TotalSalesByCustomerRootDto findAllCustomerWithAtLeastOneCar() {

        List<Customer> customers = customerRepository.findAllBySalesIsNotNull();

        TotalSalesByCustomerRootDto totalSalesByCustomerRootDto = new TotalSalesByCustomerRootDto();

        totalSalesByCustomerRootDto.setCustomers(customers
                .stream()
                .map(customer -> {
                    TotalSalesByCustomerDto dto = modelMapper.map(customer, TotalSalesByCustomerDto.class);
                    dto.setBoughtCars(customer.getSales().size());
                    dto.setFullName(customer.getName());

                    List<Car> cars = customer.getSales()
                            .stream()
                            .map(Sale::getCar).toList();

                    double totalSum = cars
                            .stream()
                            .mapToDouble(car -> {
                                return car.getParts().stream().mapToDouble(part ->
                                        Double.parseDouble(String.valueOf(part.getPrice())))
                                        .sum();
                            })
                            .sum();

                    dto.setSpentMoney(new BigDecimal(totalSum));

                    return dto;
                })
                .collect(Collectors.toList()));

        return totalSalesByCustomerRootDto;
    }
}
