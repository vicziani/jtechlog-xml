package beans;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeansTest {

    @Test
    public void testWriteToXml()  {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new BeansApi().writeToXml(catalog);

        // Then
        XmlAssert.assertThat(xml).valueByXPath("/java/object/void/object/void[position() = 1]/object/void[@property = 'title']/string/text()")
                .isEqualTo("Java and XML");
    }

    @Test
    public void testReadFromXml() {
        Catalog catalog = new BeansApi().readFromXml(new BufferedReader(new InputStreamReader(BeansTest.class.getResourceAsStream("/catalog-beans.xml"))));

        assertEquals("Java and XML", catalog.getBooks().get(0).getTitle());
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