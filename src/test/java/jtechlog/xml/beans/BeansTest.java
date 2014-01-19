package jtechlog.xml.beans;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.junit.Assert.assertEquals;

public class BeansTest {

    @Test
    public void testWriteToXml() throws SAXException, IOException, XpathException {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new BeansApi().writeToXml(catalog);

        // Then
        assertXpathEvaluatesTo("Java and XML", "/java/object/void/object/void[position() = 1]/object/void[@property = 'title']/string/text()",
                xml);
    }

    @Test
    public void testReadFromXml() {
        Catalog catalog = new BeansApi().readFromXml(BeansTest.class.getResourceAsStream("/catalog-beans.xml"));

        assertEquals("Java and XML", catalog.getBooks().get(0).getTitle());
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
        book.setIsbn10(isbn10);
        return book;
    }

}