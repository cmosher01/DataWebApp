package nu.mine.mosher.datawebapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PkgTest {
    @Test
    void self() {
        final Pkg uut = new Pkg("this.is.test");
        assertTrue(uut.isIncluded("this.is.test"));
    }

    @Test
    void nominal() {
        final Pkg uut = new Pkg("this.is.test");
        assertTrue(uut.isIncluded("this.is.test.and.yes"));
    }

    @Test
    void notIncluded() {
        final Pkg uut = new Pkg("this.is.test");
        assertFalse(uut.isIncluded("this.is.not"));
    }

    @Test
    void notIncludedSubstring() {
        final Pkg uut = new Pkg("this.is.test");
        assertFalse(uut.isIncluded("this.is.tes"));
    }

    @Test
    void notIncludedSuperString() {
        final Pkg uut = new Pkg("this.is.test");
        assertFalse(uut.isIncluded("this.is.testx"));
    }

    @Test
    void superPackage() {
        final Pkg uut = new Pkg("this.is.test");
        assertFalse(uut.isIncluded("this.is"));
    }

    @Test
    void nullPackage() {
        final Pkg uut = new Pkg("this.is.test");
        assertFalse(uut.isIncluded((Package)null));
    }

    @Test
    void emptyPackage() {
        final Pkg uut = new Pkg("this.is.test");
        assertThrows(IllegalArgumentException.class, () -> uut.isIncluded(""));
    }

    @Test
    void trailingDot() {
        final Pkg uut = new Pkg("this.is.test");
        assertThrows(IllegalArgumentException.class, () -> uut.isIncluded("this.is.test."));
    }

    @Test
    void trailingDot2() {
        final Pkg uut = new Pkg("this.is.test");
        assertThrows(IllegalArgumentException.class, () -> uut.isIncluded("this.is.test.bad."));
    }

    @Test
    void emptyPackageRoot() {
        assertThrows(IllegalArgumentException.class, () -> new Pkg(""));
    }

    @Test
    void nullPackageRoot() {
        assertThrows(NullPointerException.class, () -> new Pkg(null));
    }

    @Test
    void rootTrailingDot() {
        assertThrows(IllegalArgumentException.class, () -> new Pkg("com."));
    }

    @Test
    void rootStartingDot() {
        assertThrows(IllegalArgumentException.class, () -> new Pkg(".com"));
    }
}
