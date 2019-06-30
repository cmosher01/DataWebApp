package nu.mine.mosher.datawebapp;

import java.util.AbstractList;
import java.util.List;

public final class Table extends AbstractList {
    private final String head;
    private final List items;

    public Table(String head, List items) {
        this.head = head;
        this.items = items;
    }

    public String getHeader() {
        return this.head;
    }

    public boolean isExpandable() {
        return true;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public Object get(final int index) {
        return items.get(index);
    }
}
