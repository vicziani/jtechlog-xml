package jaxbgen;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import javax.xml.transform.stream.StreamSource;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaxbGenApiTest {

    @Test
    public void testParse() {
        // When
        Catalog catalog = new JaxbGenApi()
                .parse(new StreamSource(JaxbGenApiTest.class.getResourceAsStream("/catalog.xml")));
        // Then
        assertEquals(3, catalog.getBook().size());
        assertEquals("Java and XML", catalog.getBook().get(0).getTitle());
        assertEquals("059610149X", catalog.getBook().get(0).getIsbn10());
    }

    @Test
    public void testWrite() {
        Catalog catalog = new Catalog();
        addBookToCatalog(catalog, "059610149X", "Java and XML");
        addBookToCatalog(catalog, "1590597060", "Pro XML Development with Java Technology");

        StringWriter writer = new StringWriter();
        new JaxbGenApi().write(catalog, writer);

        System.out.println(writer.toString());

        XmlAssert.assertThat(writer.toString()).valueByXPath("//book[2]/title/text()").isEqualTo("Pro XML Development with Java Technology");
    }

    private void addBookToCatalog(Catalog catalog, String isbn10, String title) {
        Catalog.Book book = new Catalog.Book();
        book.setIsbn10(isbn10);
        book.setTitle(title);
        catalog.getBook().add(book);
    }
}
