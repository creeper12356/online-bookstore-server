package dev.bookstore.creeper.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bookstore.creeper.demo.model.BookTag;
import dev.bookstore.creeper.demo.repository.BookTagRepository;

@Component
public class BookTagDAO {
    @Autowired
    private BookTagRepository bookTagRepository;

    public boolean createTag(String name) {
        if (bookTagRepository.findByName(name) != null) {
            return false;
        }

        bookTagRepository.save(new BookTag(name));
        return true;
    }

    public boolean createRelationBetween(String tag1, String tag2) {
        BookTag bookTag1 = bookTagRepository.findByName(tag1);
        if(bookTag1 == null) {
            return false;
        }
        BookTag bookTag2 = bookTagRepository.findByName(tag2);
        if(bookTag2 == null) {
            return false;
        }

        bookTag1.relateTo(bookTag2);
        bookTagRepository.save(bookTag1);

        return true;
    }

    public List<String> getSimilarTags(String name) {
        List<BookTag> tags = bookTagRepository.findByRelatedOfRelated(name);
        return tags.stream().map(BookTag::getName).toList();
    }
}
