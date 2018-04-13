package nu.mine.mosher.datawebapp;

import java.util.Objects;

import static nu.mine.mosher.datawebapp.Utils.validatePackageName;

public final class Pkg {
    private final String root;

    public Pkg(final String root) {
        this.root = validatePackageName(root);
    }

    public boolean isIncluded(final Package p) {
        return Objects.nonNull(p) && isIncluded(p.getName());
    }

    public boolean isIncluded(final String p) {
        validatePackageName(p);
        return p.equals(this.root) || p.startsWith(this.root) && p.substring(this.root.length()).startsWith(".");
    }

}
