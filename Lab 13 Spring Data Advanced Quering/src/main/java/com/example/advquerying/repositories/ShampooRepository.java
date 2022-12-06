package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabel_IdOrderByPrice(Size size, Long labelId);

    List<Shampoo> findAllByPriceIsGreaterThanOrderByPriceDesc(BigDecimal price);

    List<Shampoo> findAllByPriceIsLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo AS s JOIN s.ingredients AS i WHERE i.name IN :ingradientsNamesList")
    List<Shampoo> findAllShampoosWithIngradientsIncludedIn(List<String> ingradientsNamesList);

    @Query("SELECT s FROM Shampoo AS s WHERE s.ingredients.size < :number")
    List<Shampoo> findAllShampoosWithIngradientsLessThan(int number);

}
