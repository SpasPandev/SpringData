package com.example.advquerying.services.Impl;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<String> findBySizeOrderById(Size size) {

        return shampooRepository.findAllBySizeOrderById(size)
                .stream()
                .map(shampoo -> String.format("%s %s %.2flv.",
                        shampoo.getBrand(),
                        shampoo.getSize().name(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBySizeOrLabelIdSortByPriceAsc(Size size, Long labelId) {

        return shampooRepository.findAllBySizeOrLabel_IdOrderByPrice(size, labelId)
                .stream()
                .map(shampoo -> String.format("%s %s %.2flv.",
                        shampoo.getBrand(),
                        shampoo.getSize().name(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllOrderByPriceDescHigherThan(BigDecimal price) {

        return shampooRepository.findAllByPriceIsGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(shampoo -> String.format("%s %s %.2flv.",
                        shampoo.getBrand(),
                        shampoo.getSize().name(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer findCountOfShampoosWithPriceLowerThan(BigDecimal price) {

        return shampooRepository.findAllByPriceIsLessThan(price).size();
    }

    @Override
    public List<String> findAllShampoosWithIngradientsIncludedIn(List<String> ingradientsNamesList) {

        return shampooRepository.findAllShampoosWithIngradientsIncludedIn(ingradientsNamesList)
                .stream()
                .map(shampoo -> String.format("%s", shampoo.getBrand()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllShampoosWithIngradientsLessThan(int number) {

        return shampooRepository.findAllShampoosWithIngradientsLessThan(number)
                .stream()
                .map(shampoo -> String.format("%s", shampoo.getBrand()))
                .collect(Collectors.toList());
    }
}
