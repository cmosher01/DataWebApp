package nu.mine.mosher.datawebapp.example;

import java.util.*;

public class Book {
    public final UUID id = UUID.randomUUID();
    public final String title;
    public final List<Author> authors;

    public Book(String title, List<Author> authors) {
        this.title = title;
        this.authors = Collections.unmodifiableList(new ArrayList<>(authors));
    }

    @Override
    public String toString() {
        return title;
    }
}
