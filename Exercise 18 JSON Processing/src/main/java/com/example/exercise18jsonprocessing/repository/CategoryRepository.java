package com.example.exercise18jsonprocessing.repository;

import com.example.exercise18jsonprocessing.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query("SELECT c FROM Category c ORDER BY c.products.size")
//    List<Category> findAllOrderByProductsCount();

    @Query(value = "SELECT *, COUNT(products_id) AS productsCount FROM categories " +
            "left outer join products_categories pc " +
            "on categories.id = pc.categories_id " +
            "GROUP BY categories.id " +
            "order by productsCount", nativeQuery = true)
    List<Category> findAllOrderByProductsCount();

}
