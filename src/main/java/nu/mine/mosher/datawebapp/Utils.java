package nu.mine.mosher.datawebapp;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

final class Utils {
    private Utils() {
        throw new IllegalStateException();
    }

    public static Optional<Object> getValue(final Field field, final Object object) {
        try {
            field.setAccessible(true);
            return Optional.ofNullable(field.get(object));
        } catch (final Throwable e) {
            return Optional.empty();
        }
    }

    public static String validatePackageName(final String p) {
        Objects.requireNonNull(p);
        if (p.isEmpty() || p.startsWith(".") || p.endsWith(".")) {
            throw new IllegalArgumentException("Invalid package name: "+p);
        }
        return p;
    }
}
