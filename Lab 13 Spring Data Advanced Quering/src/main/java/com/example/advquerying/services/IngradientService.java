package com.example.advquerying.services;

import java.util.List;

public interface IngradientService {

    List<String> findAllWithLettersStartsWith(String letters);

    List<String> findAllIngradientsContainedInGivenListOrderByPrice(List<String> namesList);

    void deleteIngradientsByGivenName(String name);

    void increasePriceOfAllIngrediantWith10Percents();

    void updatePriceOfIngrediantsWhichNamesAreIn(List<String> ingrediantsNamesList);
}
