package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.dto.OrderedCustomersRootDto;
import com.example.exercise20xmlprocessing.model.dto.TotalSalesByCustomerRootDto;
import com.example.exercise20xmlprocessing.model.entity.Customer;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface CustomerService {

    void seedCustomers(String filePath) throws JAXBException, FileNotFoundException;

    Customer getRandomCustomer();

    OrderedCustomersRootDto findAllOrderedByBirthDate();

    TotalSalesByCustomerRootDto findAllCustomerWithAtLeastOneCar();
}
