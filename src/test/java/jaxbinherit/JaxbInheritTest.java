package jaxbinherit;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xmlunit.assertj.XmlAssert;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaxbInheritTest {

    @Test
    public void testParse() {
        // When
        Catalog catalog = new JaxbApi()
                .parse(new StreamSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                        "<catalog><book>" +
                        "<title>Java and XML</title>" +
                        "</book>" + "" +
                        "<magazine>" +
                        "<title>Java Magazine - Oracle</title>" +
                        "</magazine>" +
                        "</catalog>")));
        // Then
        assertEquals(2, catalog.getItems().size());
        assertEquals(Book.class, catalog.getItems().get(0).getClass());
        assertEquals("Java and XML", catalog.getItems().get(0).getTitle());
        assertEquals(Magazine.class, catalog.getItems().get(1).getClass());
    }

    @Test
    public void testWrite() {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML"),
                createMagazine("Java Magazine - Oracle"));

        // When
        StringWriter writer = new StringWriter();
        new JaxbApi().write(catalog, writer);

        // Then

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<catalog>" +
                "<book>" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<magazine>" +
                "<title>Java Magazine - Oracle</title>" +
                "</magazine>" +
                "</catalog>";

        XmlAssert.assertThat(writer.toString()).and(expected).ignoreWhitespace().areSimilar();
    }

    private Catalog createCatalog(Item... items) {
        Catalog catalog = new Catalog();
        if (catalog.getItems() == null) {
            catalog.setItems(new ArrayList<>());
        }
        for (Item item: items) {
            catalog.getItems().add(item);
        }
        return catalog;
    }

    private Book createBook(String title) {
        Book book = new Book();
        book.setTitle(title);
        return book;
    }

    private Magazine createMagazine(String title) {
        Magazine magazine = new Magazine();
        magazine.setTitle(title);
        return magazine;
    }
}
