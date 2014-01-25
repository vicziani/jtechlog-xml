package jtechlog.xml.jaxbcycle;

import org.junit.Test;

import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class JaxbCycleTest {

    @Test
    public void testParseWithBackReference() {
        // When
        Catalog catalog = new JaxbApi()
                .parse(new StreamSource(JaxbCycleTest.class.getResourceAsStream("/catalog.xml")));
        // Then
        assertEquals(catalog, catalog.getBooks().get(0).getCatalog());
    }

    @Test
    public void testWriteNoException() {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        new JaxbApi().write(catalog);

        // Then
        // No exception
        // com.sun.istack.internal.SAXException2: A cycle is detected in the object graph. This will cause
        // infinitely deep XML:
        // jtechlog.xml.jaxbcycle.Catalog@281902 -> jtechlog.xml.jaxbcycle.Book@58ff51 -> jtechlog.xml.jaxbcycle.Catalog@281902
    }

    private Catalog createCatalog(Book... books) {
        Catalog catalog = new Catalog();
        if (catalog.getBooks() == null) {
            catalog.setBooks(new ArrayList<Book>());
        }
        for (Book book: books) {
            catalog.getBooks().add(book);
            book.setCatalog(catalog);
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
