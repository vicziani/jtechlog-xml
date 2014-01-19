package jtechlog.xml.jaxp;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.conformsTo;
import static org.xmlmatchers.XmlMatchers.hasXPath;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.validation.SchemaFactory.w3cXmlSchemaFromClasspath;

public class DomXmlMatchersTest {

    @Test
    public void testWriteXpath() {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new DomApi().write(catalog);

        // Then
        assertThat(the(xml), hasXPath("/catalog/book[@isbn10 = '1590597060']/title", equalTo("Pro XML Development with Java Technology")));
    }

    @Test
    public void testWriteEquals() {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new DomApi().write(catalog);

        // Then
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog><book isbn10=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<book isbn10=\"1590597060\">" +
                "<title>Pro XML Development with Java Technology</title>" +
                "</book>" +
                "</catalog>";
        assertThat(the(xml), isEquivalentTo(the(expected)));
    }

    @Test
    public void testWriteValid() throws SAXException {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new DomApi().write(catalog);

        // Then
        // Nem megy, a perjel van előtte
        Schema schema = w3cXmlSchemaFromClasspath("catalog.xsd");
        assertThat(the(xml), conformsTo(schema));
    }


    private Book createBook(String title, String isbn10) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn10(isbn10);
        return book;
    }

}
