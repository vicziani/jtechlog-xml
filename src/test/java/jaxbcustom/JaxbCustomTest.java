package jaxbcustom;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JaxbCustomTest {

    @Test
    public void testIsbn10() {
        assertFalse(new Isbn10("abc").isValid());
        assertFalse(new Isbn10("1234567890").isValid());
        assertTrue(new Isbn10("059610149X").isValid());
        assertTrue(new Isbn10("1590597060").isValid());
    }

    @Test
    public void testParse() {
        // When
        Catalog catalog = new JaxbApi()
                .parse(new StreamSource(JaxbCustomTest.class.getResourceAsStream("/catalog.xml")));
        // Then
        assertEquals(3, catalog.getBooks().size());
        assertEquals("Java and XML", catalog.getBooks().get(0).getTitle());
        assertEquals("059610149X", catalog.getBooks().get(0).getIsbn10().getValue());
        assertTrue(catalog.getBooks().get(0).getIsbn10().isValid());
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
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<catalog>" +
                "<book isbn10='059610149X'>" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<book isbn10='1590597060'>" +
                "<title>Pro XML Development with Java Technology</title>" +
                "</book>" +
                "</catalog>";

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
        Isbn10 isbn = new Isbn10(isbn10);
        book.setIsbn10(isbn);
        return book;
    }
}
