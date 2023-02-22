package com.example.exercise18jsonprocessing.service;

import com.example.exercise18jsonprocessing.model.dto.SalesInfoAboutCarDto;

import java.util.List;

public interface SaleService {
    void seedSale();

    List<SalesInfoAboutCarDto> findAllSales();
}
