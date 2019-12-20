package nu.mine.mosher.datawebapp.example;

import java.util.UUID;

public class Author {
    public final UUID id = UUID.randomUUID();
    public final String name;

    public Author(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
