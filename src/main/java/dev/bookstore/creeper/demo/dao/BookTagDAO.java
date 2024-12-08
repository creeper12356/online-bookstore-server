package dev.bookstore.creeper.demo.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        if(tag1.equals(tag2)) {
            return false;
        }

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

    public boolean removeRelationshipBetween(String tag1, String tag2) {
        if(tag1.equals(tag2)) {
            return false;
        }
        BookTag bookTag1 = bookTagRepository.findByName(tag1);
        if(bookTag1 == null) {
            return false;
        }
        BookTag bookTag2 = bookTagRepository.findByName(tag2);
        if(bookTag2 == null) {
            return false;
        }

        bookTag1.unrelatedTo(bookTag2);
        bookTagRepository.save(bookTag1);

        return true;
    }

    public Set<String> getSimilarTags(String name) {
        Set<BookTag> relatedOfRelatedTags = bookTagRepository.findByRelatedOfRelated(name);
        Set<BookTag> relatedBookTags = bookTagRepository.findByRelatedName(name);
        BookTag tag = bookTagRepository.findByName(name);

        Set<BookTag> tags = new HashSet<>();
        tags.addAll(relatedOfRelatedTags);
        tags.addAll(relatedBookTags);
        tags.add(tag);
        
        return tags.stream().map(BookTag::getName).collect(Collectors.toSet());
    }
}
