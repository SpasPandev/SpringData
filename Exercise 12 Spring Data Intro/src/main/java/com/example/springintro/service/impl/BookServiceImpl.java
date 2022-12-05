package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

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

        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .stream()
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = new Book(
                            EditionType.values()[Integer.parseInt(bookInfo[0])],
                            LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy")),
                            Integer.parseInt(bookInfo[2]),
                            new BigDecimal(bookInfo[3]),
                            AgeRestriction.values()[Integer.parseInt(bookInfo[4])],
                            Arrays.stream(bookInfo).skip(5).collect(Collectors.joining(" "))
                    );

                    book.setAuthor(authorService.getRandomAuthor());

                    book.setCategories(categoryService.getRandomCategories());

                    bookRepository.save(book);
                });

    }

    @Override
    public List<Book> findAllBooksTitelsReleasedDateAfter(int year) {
        return bookRepository.findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksReleasedBefore(int year) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle(String firstName, String lastName) {
        return bookRepository.findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }
}
