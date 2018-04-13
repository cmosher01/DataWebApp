package nu.mine.mosher.datawebapp;

import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static nu.mine.mosher.datawebapp.Utils.getValue;

public class DataWebApp {
    private final Pkg pkg;
    private final STGroupFile stg;

    public DataWebApp(final String rootPackage) {
        this.stg = new STGroupFile("nu/mine/mosher/datawebapp/DataWebApp.stg");
        this.pkg = new Pkg(rootPackage);
    }

    public String page(final Object x) {
        return this.stg
            .getInstanceOf("entities")
            .add("x", x)
            .add("rl", links(x))
            .render();
    }

    private List<Table> links(final Object x) {
        final List<Table> rl = new ArrayList<>();
        Arrays.stream(x.getClass().getDeclaredFields()).forEach(field ->
            getValue(field, x).ifPresent(value ->
                getLinks(field.getType(), value).ifPresent(links ->
                    rl.add(new Table(field.getName(), links))
                )
            )
        );
        return rl;
    }

    private Optional<List> getLinks(final Class ft, final Object val) {
        if (val instanceof Collection) {
            // Collection
            final Collection col = (Collection)val;
            if (!col.isEmpty()) {
                if (this.pkg.isIncluded(col.iterator().next().getClass().getPackage())) {
                    return Optional.of(new ArrayList(col));
                }
            }
        }
        else if (val.getClass().isArray()) {
            // array
            final Object[] arr = (Object[])val;
            if (arr.length > 0) {
                if (this.pkg.isIncluded(ft.getComponentType().getPackage())) {
                    return Optional.of(asList(arr));
                }
            }
        }
        else {
            // scalar
            if (this.pkg.isIncluded(ft.getPackage())) {
                return Optional.of(singletonList(val));
            }
        }
        return Optional.empty();
    }
}
