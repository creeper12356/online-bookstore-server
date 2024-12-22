package dev.bookstore.creeper.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.service.BookService;

@Controller
public class GraphQLBookController {

    @Autowired
    private BookService bookService;

    @QueryMapping
    public List<Book> bookByTitle(@Argument String title) {
        return bookService.getAllBooks(title);
    }
}