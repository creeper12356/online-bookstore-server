package dev.bookstore.creeper.demo.repository;

import java.util.Set;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.BookTag;

@Repository
public interface BookTagRepository extends Neo4jRepository<BookTag, Long>{
    BookTag findByName(String name);

    @Query(value = "MATCH (bookTag:BookTag) - [:RELATED]-(related) WHERE bookTag.name=$0 RETURN related")
    Set<BookTag> findByRelatedName(String name);

    @Query(value = "MATCH (bookTag:BookTag) - [:RELATED]-(related) - [:RELATED]-(related) WHERE bookTag.name=$0 RETURN DISTINCT related")
    Set<BookTag> findByRelatedOfRelated(String name);

    @Query("MATCH (bookTag:BookTag {name: $0}) SET bookTag.related = $1 RETURN bookTag")
    BookTag updateRelated(String name, Set<BookTag> related);

    void removeByName(String name);
}
