package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngradientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameStartsWith(String letters);

    List<Ingredient> findAllByNameInOrderByPrice(List<String> namesList);

    @Modifying
    @Query("DELETE FROM Ingredient AS i WHERE i.name LIKE :name")
    void deleteByName(String name);

    @Modifying
    @Query("UPDATE Ingredient AS i SET i.price = i.price + (i.price * 0.1)")
    void increasePriceOfAllIngrediantWith10Percents();

    @Modifying
    @Query("UPDATE Ingredient AS i SET i.price = i.price + 100 WHERE i.name IN :ingrediantsNamesList")
    void updatePriceOfIngrediantsWhichNamesAreIn(List<String> ingrediantsNamesList);

}
