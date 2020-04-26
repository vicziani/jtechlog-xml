package dom;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xmlunit.assertj.XmlAssert;
import validator.ValidatorApi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomTest {

    @Test
    public void testParse() {
        // When
        List<Book> books = new DomApi()
                .parse(new BufferedReader(new InputStreamReader(DomTest.class.getResourceAsStream("/catalog.xml"))));
        // Then
        assertEquals(3, books.size());
        assertEquals("Java and XML", books.get(0).getTitle());
        assertEquals("059610149X", books.get(0).getIsbn10());
    }

    @Test
    public void testParseWithIterator() {
        // When
        List<Book> books = new DomApi()
                .parseWithIterator(new BufferedReader(new InputStreamReader(DomTest.class.getResourceAsStream("/catalog.xml"))));
        // Then
        assertEquals(3, books.size());
        assertEquals("Java and XML", books.get(0).getTitle());
        assertEquals("059610149X", books.get(0).getIsbn10());
    }

    @Test
    public void testWrite() {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        StringWriter writer = new StringWriter();
        new DomApi().write(catalog, writer);

        // Then
        String expected =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<catalog>\n" +
                        "  <book isbn10=\"059610149X\">\n" +
                        "    <title>Java and XML</title>\n" +
                        "  </book>\n" +
                        "  <book isbn10=\"1590597060\">\n" +
                        "    <title>Pro XML Development with Java Technology</title>\n" +
                        "  </book>\n" +
                        "</catalog>\n";

        XmlAssert.assertThat(writer.toString()).and(expected).areSimilar();
    }

    @Test
    public void testWriteXpath() {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        StringWriter writer = new StringWriter();
        new DomApi().write(catalog, writer);

        // Then
        XmlAssert.assertThat(writer.toString()).valueByXPath("/catalog/book[@isbn10 = '1590597060']/title")
                .isEqualTo("Pro XML Development with Java Technology");

    }

    @Test
    public void testWriteValidate() {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        StringWriter writer = new StringWriter();
        new DomApi().write(catalog, writer);

        // Then
        XmlAssert.assertThat(writer.toString()).isValidAgainst(ValidatorApi.class.getResourceAsStream("/catalog.xsd"));
    }

    private Book createBook(String title, String isbn10) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn10(isbn10);
        return book;
    }
}
