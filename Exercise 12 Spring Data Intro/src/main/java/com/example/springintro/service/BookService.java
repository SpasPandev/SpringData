package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksTitelsReleasedDateAfter(int year);

    List<String> findAllAuthorsWithBooksReleasedBefore(int year);

    List<String> findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle(String firstName, String lastName);

}
