package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.enums.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findBooksTitelsByAgeRestrictionMatchinWith(String ageRestrictionType);

    List<String> findBookTitelsByEditionTypeAndNumberOfCopies(EditionType editionType, int copies);

    List<String> findBooksTitelsAndPriceWithPriceLowerThanAndHigherThan(BigDecimal priceLower, BigDecimal priceHigher);

    List<String> findTitelsOfBooksThatAreNotReleasedInYear(int year);

    List<String> findTitelsEditionTypeAndPriceOfBooksReleasedBefore(LocalDate date);

    List<String> findTitelOfBooksWhichTitelContains(String str);

    List<String> findTitelsOfBooksWhoseAuthorsLastNameStartsWith(String startWith);

    Long findCountOfBooksWhoseTitelLenghtMoreThan(int number);

    List<String> findInformationForBookByGivenTitle(String title);

    int updateCopiesOfAllBooksreleasedAfterGivenDateWithGivenNumber(LocalDate givenDate, int givenNumber);

    int removeBooksWithCopiesLessThan(int num);

    String procedureGetNumberOfBooksWrittenByAuthorWithFNameAndLName(String firstName, String lastName);

}
