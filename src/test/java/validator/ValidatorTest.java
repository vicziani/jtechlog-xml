package validator;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {

    private static final String VALID_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
            "<catalog><book isbn10=\"059610149X\">" +
            "<title>Java and XML</title>" +
            "</book>" + "" +
            "<book isbn10=\"1590597060\">" +
            "<title>Pro XML Development with Java Technology</title>" +
            "</book>" +
            "</catalog>";

    private static final String VALID_XML_WITH_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
            "<!DOCTYPE catalog SYSTEM \"catalog.dtd\">" +
            "<catalog><book isbn10=\"059610149X\">" +
            "<title>Java and XML</title>" +
            "</book>" + "" +
            "<book isbn10=\"1590597060\">" +
            "<title>Pro XML Development with Java Technology</title>" +
            "</book>" +
            "</catalog>";

    private static final String NOT_WELL_FORMED_XML =  "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
            "<catalog><book isbn10=\"059610149X\">" +
            "<title>Java and XML</title>" +
            "</book>";

    // invalid attr
    private static final String NOT_VALID_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
            "<!DOCTYPE catalog SYSTEM \"catalog.dtd\">" +
            "<catalog><book isbn=\"059610149X\">" +
            "<title>Java and XML</title>" +
            "</book>" +
            "</catalog>";

    @Test
    public void testValidateByXsd() {
        // When
        boolean valid = new ValidatorApi().validateByXsd(VALID_XML);

        // Then
        assertTrue(valid, "Must be valid");
    }

    @Test
    public void testNotWellFormedByXsd() {
        // When
        boolean valid = new ValidatorApi().validateByXsd(NOT_WELL_FORMED_XML);

        // Then
        assertFalse(valid, "Must be invalid");
    }

    @Test
    public void testNotValidByXsd() {
        // When
        boolean valid = new ValidatorApi().validateByXsd(NOT_VALID_XML);

        // Then
        assertFalse(valid, "Must be invalid");
    }

    @Test
    public void testValidateByDtdDom() {
        // When
        boolean valid = new ValidatorApi().validateByDtdDom(VALID_XML_WITH_DTD);

        // Then
        assertTrue(valid, "Must be valid");
    }

    @Test
    public void testNotWellFormedByDtdDom() {
        // When
        boolean valid = new ValidatorApi().validateByDtdDom(NOT_WELL_FORMED_XML);

        // Then
        assertFalse(valid, "Must be invalid");
    }

    @Test
    public void testNotValidByDtdDom() {
        // When
        boolean valid = new ValidatorApi().validateByDtdDom(NOT_VALID_XML);

        // Then
        assertFalse(valid, "Must be invalid");
    }

    @Test
    public void testValidateByDtdSax() {
        // When
        boolean valid = new ValidatorApi().validateByDtdSax(VALID_XML_WITH_DTD);

        // Then
        assertTrue(valid, "Must be valid");
    }

    @Test
    public void testNotWellFormedByDtdSax() {
        // When
        boolean valid = new ValidatorApi().validateByDtdSax(NOT_WELL_FORMED_XML);

        // Then
        assertFalse(valid, "Must be invalid");
    }

    @Test
    public void testNotValidByDtdSax() {
        // When
        boolean valid = new ValidatorApi().validateByDtdSax(NOT_VALID_XML);

        // Then
        assertFalse(valid, "Must be invalid");
    }


    // JDK-ban lévő StAX implementáció nem támogatja a DTD alapú validációt
}
