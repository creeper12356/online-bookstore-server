package dev.bookstore.creeper.demo.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node
@Data
public class BookTag {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "RELATED")
    public Set<BookTag> related;

    public void relateTo(BookTag tag) {
        if (related == null) {
            related = new HashSet<>();
        }

        related.add(tag);
    }

    private BookTag() {
        // private constructor required by Neo4j
    }

    public BookTag(String name) {
        this.name = name;
    }
}
