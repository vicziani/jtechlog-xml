package xpath;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class XpathTest {

    @Test
    public void testEvalAsString() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog><book isbn10=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<book isbn10=\"1590597060\">" +
                "<title>Pro XML Development with Java Technology</title>" +
                "</book>" +
                "</catalog>";

        // When
        String result = new XpathApi().evalAsString(xml, "/catalog/book[position() = 2]/title");

        // Then
        assertEquals("Pro XML Development with Java Technology", result);
    }

    @Test
    public void testValidateIsbn() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog><book isbn10=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "</catalog>";

        // When
        boolean result = new XpathApi().validateIsbn(xml);

        // Then
        assertTrue(result);

    }

    @Test
    public void testValidateIsbnInvalid() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog><book isbn10=\"059610149Y\">" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "</catalog>";

        // When
        boolean result = new XpathApi().validateIsbn(xml);

        // Then
        assertFalse(result);

    }
}
