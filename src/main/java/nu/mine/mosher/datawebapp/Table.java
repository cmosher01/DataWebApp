package nu.mine.mosher.datawebapp;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public final class Table
{
    private final String head;
    private final List items;

    Table(String head, List items) {
        this.head = head;
        this.items = items;
    }

    public String getHead() {
        return this.head;
    }

    public List getItems() {
        return unmodifiableList(this.items);
    }
}
