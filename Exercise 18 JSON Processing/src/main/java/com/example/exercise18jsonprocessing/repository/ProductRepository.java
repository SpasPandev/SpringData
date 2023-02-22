package com.example.exercise18jsonprocessing.repository;

import com.example.exercise18jsonprocessing.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.price between ?1 and ?2 and p.buyer is null order by p.price")
    List<Product> findAllByPriceBetweenAndAndBuyerIsNullOrderByPrice(BigDecimal lower, BigDecimal upper);
}
