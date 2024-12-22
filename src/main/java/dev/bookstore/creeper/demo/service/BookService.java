package dev.bookstore.creeper.demo.service;

import java.util.Date;
import java.util.List;

import dev.bookstore.creeper.demo.dto.BookSalesDTO;
import dev.bookstore.creeper.demo.dto.GetAllBooksOkResponseDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.dto.UpdateBookInfoDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Comment;

public interface BookService {
    GetAllBooksOkResponseDTO getAllBooks(String q, Integer page, Integer pagesize);
    Book getBookInfo(Integer id);
    List<Comment> getBookComments(Integer id);
    Integer createBook(Integer userId, UpdateBookInfoDTO book) throws Exception;
    void updateBookInfo(Integer userId, Integer bookId, UpdateBookInfoDTO book) throws Exception;
    void deleteBook(Integer userId, Integer bookId) throws Exception;
    void createBookComment(Integer userId, Integer id, String content);


    GetItemsOkDTO<BookSalesDTO> getBookRank(Integer currentUserId, Date from, Date to, Integer maxCount)  throws Exception;

    List<Book> getSimilarBooks(Integer id);
    List<String> getBookTags(Integer id);
    void updateBookTags(Integer id, List<String> tags);
}
