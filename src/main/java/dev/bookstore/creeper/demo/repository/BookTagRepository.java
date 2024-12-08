package dev.bookstore.creeper.demo.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.BookTag;

@Repository
public interface BookTagRepository extends Neo4jRepository<BookTag, Long>{
    BookTag findByName(String name);

    List<BookTag> findByRelatedName(String name);

    @Query(value = "MATCH (bookTag:BookTag) - [:RELATED]->(related) - [:RELATED]->(related) WHERE bookTag.name=$0 RETURN related")
    List<BookTag> findByRelatedOfRelated(String name);
}
