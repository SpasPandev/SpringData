package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        seedCategories();
        seedAuthors();
        seedBooks();

        printBooksTitelsReleasedAfter(2000);
        printAuthorsWithBooksReleasedBefore(1990);

        printAuthorsOrderByNumberOfBooks();

        printBooksByAuthorOrderBuReleaseDateAndBookTitle("George", "Powell");

    }

    private void printBooksByAuthorOrderBuReleaseDateAndBookTitle(String firstName , String lastName) {

        bookService.findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAuthorsOrderByNumberOfBooks() {

        authorService.getAllAuthorsOrderByNumberOfBooks()
                .forEach(System.out::println);
    }


    private void printAuthorsWithBooksReleasedBefore(int year) {

        bookService.findAllAuthorsWithBooksReleasedBefore(year)
                .forEach(System.out::println);
    }

    private void printBooksTitelsReleasedAfter(int year) {

        bookService.findAllBooksTitelsReleasedDateAfter(year)
                .stream()
                .forEach(book -> System.out.println(book.getTitle()));
    }

    private void seedBooks() throws IOException {

        bookService.seedBooks();
    }

    private void seedAuthors() throws IOException {

        authorService.seedAuthors();

    }

    private void seedCategories() throws IOException {

        categoryService.seedCategories();
    }
}
