package com.example.advquerying.services.Impl;

import com.example.advquerying.repositories.IngradientRepository;
import com.example.advquerying.services.IngradientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngradientServiceImpl implements IngradientService {

    private final IngradientRepository ingradientRepository;

    public IngradientServiceImpl(IngradientRepository ingradientRepository) {
        this.ingradientRepository = ingradientRepository;
    }

    @Override
    public List<String> findAllWithLettersStartsWith(String letters) {

        return ingradientRepository.findAllByNameStartsWith(letters)
                .stream()
                .map(ingredient -> String.format("%s", ingredient.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllIngradientsContainedInGivenListOrderByPrice(List<String> namesList) {

        return ingradientRepository.findAllByNameInOrderByPrice(namesList)
                .stream()
                .map(ingradient -> String.format("%s", ingradient.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteIngradientsByGivenName(String name) {

        ingradientRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void increasePriceOfAllIngrediantWith10Percents() {

        ingradientRepository.increasePriceOfAllIngrediantWith10Percents();
    }

    @Override
    @Transactional
    public void updatePriceOfIngrediantsWhichNamesAreIn(List<String> ingrediantsNamesList) {

        ingradientRepository.updatePriceOfIngrediantsWhichNamesAreIn(ingrediantsNamesList);
    }


}
