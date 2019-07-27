package jaxbns;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaxbTest {

    @Test
    public void testParse() {
        // When
        Catalog catalog = new JaxbApi()
                .parse(new StreamSource(JaxbTest.class.getResourceAsStream("/catalog-ns.xml")));
        // Then
        assertEquals(3, catalog.getBooks().size());
        assertEquals("Java and XML", catalog.getBooks().get(0).getTitle());
        assertEquals("059610149X", catalog.getBooks().get(0).getIsbn10());
    }

    @Test
    public void testWrite() {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        StringWriter writer = new StringWriter();
        new JaxbApi().write(catalog, writer);

        // Then
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns2:catalog xmlns:ns2=\"http://www.jtechlog.hu/catalog\">\n" +
                "    <book isbn10=\"059610149X\">\n" +
                "        <title>Java and XML</title>\n" +
                "    </book>\n" +
                "    <book isbn10=\"1590597060\">\n" +
                "        <title>Pro XML Development with Java Technology</title>\n" +
                "    </book>\n" +
                "</ns2:catalog>";

        XmlAssert.assertThat(writer.toString()).and(expected).ignoreWhitespace().areSimilar();
    }

    private Catalog createCatalog(Book... books) {
        Catalog catalog = new Catalog();
        if (catalog.getBooks() == null) {
            catalog.setBooks(new ArrayList<>());
        }
        for (Book book: books) {
            catalog.getBooks().add(book);
        }
        return catalog;
    }

    private Book createBook(String title, String isbn10) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn10(isbn10);
        return book;
    }
}
