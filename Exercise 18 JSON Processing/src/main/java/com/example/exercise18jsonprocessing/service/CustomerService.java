package com.example.exercise18jsonprocessing.service;

import com.example.exercise18jsonprocessing.model.dto.CustomerWithTotalSalesDto;
import com.example.exercise18jsonprocessing.model.dto.OrderedCustomersDto;
import com.example.exercise18jsonprocessing.model.entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    void seedCustomer() throws IOException;

    Customer getRandomCustomer();

    List<OrderedCustomersDto> getAllCustomersOrderedByBirthday();

    List<CustomerWithTotalSalesDto> getAllCustomersWithBoughtCars();
}
