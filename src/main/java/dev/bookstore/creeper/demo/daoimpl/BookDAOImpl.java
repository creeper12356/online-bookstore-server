package dev.bookstore.creeper.demo.daoimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.dao.BookTagDAO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.repository.BookRepository;

@Component
public class BookDAOImpl implements BookDAO {
    private final BookRepository bookRepository;

    @Autowired
    private BookTagDAO bookTagDAO;

    public BookDAOImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks(String q) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> q.isEmpty() || book.getTitle().toLowerCase().contains(q.toLowerCase()))
                .toList();
    }

    @Override
    public Optional<Book> findBookById(Integer id) {

        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook;
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooksById(List<Integer> ids) {
        return bookRepository.findAllById(ids);
    }

    @Override
    public void saveAllBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> findSimilarBooksByTags(Book book) {
        List<String> tagNameList = book.getTags();
        Set<String> similarTagNameSet = new HashSet<>();

        for (String tagName : tagNameList) {
            List<String> res = bookTagDAO.getSimilarTags(tagName);
            similarTagNameSet.addAll(res);
        }

        return bookRepository.findBookContainsTag(similarTagNameSet);
    }

}
