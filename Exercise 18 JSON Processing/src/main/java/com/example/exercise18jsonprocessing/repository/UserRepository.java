package com.example.exercise18jsonprocessing.repository;

import com.example.exercise18jsonprocessing.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "WHERE (SELECT COUNT(p) FROM Product p WHERE p.seller.id = u.id) > 0 " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllUserWithMoreThanOneSoldProductsOrderByLastNameThenByFirstName();

//    @Query(value = "SELECT *, COUNT(p.seller_id) AS bn FROM ex18json_processing.users AS u " +
//            "JOIN ex18json_processing.products AS p " +
//            "ON u.id = p.seller_id " +
//            "GROUP BY p.seller_id " +
//            "ORDER BY bn DESC", nativeQuery = true)
//    @Query("select u from User u " +
//            "where u.soldProducts.size >0")
//    List<User> findAllUsersWithSoldProductsOrderBySoldProductsDescThenByLastName();


    List<User> findAllBySoldProductsIsNotNullOrderBySoldProductsDescLastName();
//    @Query("SELECT u FROM User u WHERE User.soldProducts.size >0")
//    List<User> findAllUsersWithSoldProductsOrderBySoldProductsDescThenByLastName();
}
