package nu.mine.mosher.datawebapp;

import fi.iki.elonen.NanoHTTPD;
import nu.mine.mosher.datawebapp.example.*;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.util.*;

import static java.lang.Runtime.getRuntime;
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


    public static void main(String[] args) throws IOException {
        final List<Book> model = new ArrayList<>();

        final List<Author> goodOmensAuthors = new ArrayList<>();
        goodOmensAuthors.add(new Author("Neil Gaiman"));
        goodOmensAuthors.add(new Author("Terry Pratchett"));
        model.add(new Book("Good Omens", goodOmensAuthors));

        final Author stephenKing = new Author("Stephen King");

        final List<Author> talismanAuthors = new ArrayList<>();
        talismanAuthors.add(stephenKing);
        talismanAuthors.add(new Author("Peter Straub"));
        model.add(new Book("Talisman", talismanAuthors));

        model.add(new Book("Carrie", Collections.singletonList(stephenKing)));



        final DataWebApp dataWebApp = new DataWebApp(Book.class.getPackage().getName());
        final NanoHTTPD server = new NanoHTTPD(8080) {
            @Override
            public Response serve(final IHTTPSession http) {
                return newFixedLengthResponse(dataWebApp.page(model));
            }
        };

        server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);

        getRuntime().addShutdownHook(new Thread(server::stop));
    }
}
