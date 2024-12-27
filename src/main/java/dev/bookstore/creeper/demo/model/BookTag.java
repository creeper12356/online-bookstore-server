package dev.bookstore.creeper.demo.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class BookTag {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 维护双向关系，参考： https://graphaware.com/blog/neo4j-bidirectional-relationships/
    @Relationship(type = "RELATED")
    public Set<BookTag> related = new HashSet<>();

    public String getName() {
        return name;
    }

    public Set<BookTag> getRelated() {
        return related;
    }

    public void relateTo(BookTag bookTag) {
        related.add(bookTag);
    }

    public void unrelateTo(BookTag bookTag) {
        related.remove(bookTag);
        bookTag.related.remove(this);
    }

    private BookTag() {
        // private constructor required by Neo4j
    }

    public BookTag(String name) {
        this.name = name;
    }
}
