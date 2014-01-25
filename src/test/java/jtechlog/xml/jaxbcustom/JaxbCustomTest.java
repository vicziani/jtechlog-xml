package jtechlog.xml.jaxbcustom;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.ArrayList;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void testWrite() throws IOException, SAXException {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new JaxbApi().write(catalog);

        // Then
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog>" +
                "<book isbn10='059610149X'>" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<book isbn10='1590597060'>" +
                "<title>Pro XML Development with Java Technology</title>" +
                "</book>" +
                "</catalog>", xml);
    }

    private Catalog createCatalog(Book... books) {
        Catalog catalog = new Catalog();
        if (catalog.getBooks() == null) {
            catalog.setBooks(new ArrayList<Book>());
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
