package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.dto.SalesInfoRootDto;

public interface SaleService {

    void seedSales();

    SalesInfoRootDto findAllSales();
}
