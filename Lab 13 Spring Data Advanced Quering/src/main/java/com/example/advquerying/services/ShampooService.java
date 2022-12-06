package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


public interface ShampooService {

    List<String> findBySizeOrderById(Size size);

    List<String> findAllBySizeOrLabelIdSortByPriceAsc(Size size, Long labelId);

    List<String> findAllOrderByPriceDescHigherThan(BigDecimal price);

    Integer findCountOfShampoosWithPriceLowerThan(BigDecimal price);

    List<String> findAllShampoosWithIngradientsIncludedIn (List<String> ingradientsNamesList);

    List<String> findAllShampoosWithIngradientsLessThan (int number);

}
