package com.example.springintro.service.impl;

import com.example.springintro.model.enums.AgeRestriction;
import com.example.springintro.model.entity.*;
import com.example.springintro.model.enums.EditionType;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBooksTitelsByAgeRestrictionMatchinWith(String ageRestrictionType) {

        return bookRepository.findAllByAgeRestrictionLike(AgeRestriction.valueOf(ageRestrictionType))
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBookTitelsByEditionTypeAndNumberOfCopies(EditionType editionType, int copies) {

        return bookRepository.findAllByEditionTypeAndCopiesLessThan(editionType, copies)
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBooksTitelsAndPriceWithPriceLowerThanAndHigherThan(BigDecimal priceLower, BigDecimal priceHigher) {

        return bookRepository.findAllByPriceIsLessThanOrPriceIsGreaterThan(priceLower, priceHigher)
                .stream()
                .map(book -> String.format("%s - %.2f", book.getTitle(), book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitelsOfBooksThatAreNotReleasedInYear(int year) {

        return bookRepository.findBooksByReleaseDateBeforeOrReleaseDateAfter(LocalDate.of(year, 01, 01),
                LocalDate.of(year, 12, 31))
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitelsEditionTypeAndPriceOfBooksReleasedBefore(LocalDate date) {

        return bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(book -> String.format("%s %s %.2f",
                        book.getTitle(),
                        book.getEditionType().name(),
                        book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitelOfBooksWhichTitelContains(String str) {

        return bookRepository.findAllByTitleContaining(str)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitelsOfBooksWhoseAuthorsLastNameStartsWith(String startWith) {

        return bookRepository.findBooksByAuthorLastNameStartingWith(startWith)
                .stream()
                .map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public Long findCountOfBooksWhoseTitelLenghtMoreThan(int number) {

        return bookRepository.findBooksWithTitelLenghtMoreThan(number)
                .stream().
                count();
    }

    @Override
    public List<String> findInformationForBookByGivenTitle(String title) {

        return bookRepository.findAllByTitle(title)
                .stream()
                .map(book -> String.format("%s %s %s %.2f",
                        book.getTitle(),
                        book.getEditionType().name(),
                        book.getAgeRestriction().name(),
                        book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int updateCopiesOfAllBooksreleasedAfterGivenDateWithGivenNumber(LocalDate givenDate, int givenNumber) {

        int updatedBooks = bookRepository.updateCopiesOfAllBooksreleasedAfterGivenDateWithGivenNumber(givenDate, givenNumber);

        return updatedBooks * givenNumber;
    }

    @Override
    @Transactional
    public int removeBooksWithCopiesLessThan(int num) {

        return bookRepository.removeAllByCopiesLessThan(num);
    }

    @Override
    public String procedureGetNumberOfBooksWrittenByAuthorWithFNameAndLName(String firstName, String lastName) {

        return String.format("%s and %s has writtent %d books",
                firstName,
                lastName,
                bookRepository.procedureCountBooksByGivenFirstNameAndLastName(firstName, lastName));
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
