package com.example.springintro.service;

import com.example.springintro.model.entity.Author;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByNumberOfBooks();


}
