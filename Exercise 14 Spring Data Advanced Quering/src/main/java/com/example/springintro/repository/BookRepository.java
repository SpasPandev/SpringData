package com.example.springintro.repository;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.enums.AgeRestriction;
import com.example.springintro.model.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestrictionLike(AgeRestriction ageRestrictionType);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal priceLower, BigDecimal priceHigher);

    List<Book> findBooksByReleaseDateBeforeOrReleaseDateAfter(LocalDate beforeDate, LocalDate afterDate);

    List<Book> findBooksByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContaining(String str);

    List<Book> findBooksByAuthorLastNameStartingWith(String startsWith);

    @Query("SELECT b FROM Book AS b WHERE LENGTH(b.title) > :number ")
    List<Book> findBooksWithTitelLenghtMoreThan(int number);

    List<Book> findAllByTitle(String title);

    @Query("UPDATE Book SET copies = copies + :givenNumber WHERE releaseDate > :givenDate")
    @Modifying
    int updateCopiesOfAllBooksreleasedAfterGivenDateWithGivenNumber(LocalDate givenDate, int givenNumber);

    @Modifying
    int removeAllByCopiesLessThan(int number);

    @Query(value = "CALL get_total_amount_of_books_written_by_author(:firstName, :lastName)", nativeQuery = true)
    int procedureCountBooksByGivenFirstNameAndLastName(String firstName, String lastName);
}
