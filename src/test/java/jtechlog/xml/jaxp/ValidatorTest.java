package jtechlog.xml.jaxp;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {

    @Test
    public void testValidateByXsd() {
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
        boolean valid = new ValidatorApi().validateByXsd(xml);

        // Then
        assertTrue("Must be valid", valid);
    }

    @Test
    public void testNotWellFormedByXsd() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog><book isbn10=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>";

        // When
        boolean valid = new ValidatorApi().validateByXsd(xml);

        // Then
        assertFalse("Must be invalid", valid);
    }

    @Test
    public void testNotValidByXsd() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog><book isbn=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>" +
                "</catalog>";

        // When
        boolean valid = new ValidatorApi().validateByXsd(xml);

        // Then
        assertFalse("Must be invalid", valid);
    }

    @Test
    public void testValidateByDtd() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<!DOCTYPE catalog SYSTEM \"catalog.dtd\">" +
                "<catalog><book isbn10=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<book isbn10=\"1590597060\">" +
                "<title>Pro XML Development with Java Technology</title>" +
                "</book>" +
                "</catalog>";

        // When
        boolean valid = new ValidatorApi().validateByDtd(xml);

        // Then
        assertTrue("Must be valid", valid);
    }

    @Test
    public void testNotWellFormedByDtd() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<!DOCTYPE catalog SYSTEM \"catalog.dtd\">" +
                "<catalog><book isbn10=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>";

        // When
        boolean valid = new ValidatorApi().validateByDtd(xml);

        // Then
        assertFalse("Must be invalid", valid);
    }

    @Test
    public void testNotValidByDtd() {
        // Given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<!DOCTYPE catalog SYSTEM \"catalog.dtd\">" +
                "<catalog><book isbn=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>" +
                "</catalog>";

        // When
        boolean valid = new ValidatorApi().validateByDtd(xml);

        // Then
        assertFalse("Must be invalid", valid);
    }
}
