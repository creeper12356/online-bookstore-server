package dev.bookstore.creeper.demo.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.BookTagDAO;

@Service
public class BookTagService {
    @Autowired
    private BookTagDAO bookTagDAO;

    public void createRelationBetween(String tag1, String tag2) {
        boolean res = bookTagDAO.createRelationBetween(tag1, tag2);
        if(!res) {
            throw new IllegalArgumentException("Invalid tags");
        }
    }

    public void removeRelationshipBetween(String tag1, String tag2) {
        boolean res = bookTagDAO.removeRelationshipBetween(tag1, tag2);
        if(!res) {
            throw new IllegalArgumentException("Invalid tags");
        }
    }

    public void createTag(String tag) {
        boolean res = bookTagDAO.createTag(tag);
        if(!res) {
            throw new IllegalArgumentException("Tag already exists");
        }
    }

    public void removeTag(String tag) {
        boolean res = bookTagDAO.removeTag(tag);
        if(!res) {
            throw new IllegalArgumentException("Tag does not exist");
        }
    }

    public void removeAllTags() {
        bookTagDAO.removeAllTags();
    }

    public Set<String> getAllTags() {
        return bookTagDAO.getAllTags();
    }
}
