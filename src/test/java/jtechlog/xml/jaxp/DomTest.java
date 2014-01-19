package jtechlog.xml.jaxp;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.custommonkey.xmlunit.jaxp13.Validator;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.CodeSource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DomTest {

    @Test
    public void testJdkVersion() {
        System.out.println(com.sun.org.apache.xerces.internal.impl.Version.getVersion());
        System.out.println(com.sun.org.apache.xalan.internal.Version.getVersion());
    }

    @Test
    public void testVersion() {
        System.out.println(getJaxpImplementationInfo("DocumentBuilderFactory", DocumentBuilderFactory.newInstance().getClass()));
        System.out.println(getJaxpImplementationInfo("XPathFactory", XPathFactory.newInstance().getClass()));
        System.out.println(getJaxpImplementationInfo("TransformerFactory", TransformerFactory.newInstance().getClass()));
        System.out.println(getJaxpImplementationInfo("SAXParserFactory", SAXParserFactory.newInstance().getClass()));
    }

    private static String getJaxpImplementationInfo(String componentName, Class componentClass) {
        CodeSource source = componentClass.getProtectionDomain().getCodeSource();
        return MessageFormat.format(
                "{0} implementation: {1} loaded from: {2}",
                componentName,
                componentClass.getName(),
                source == null ? "Java Runtime" : source.getLocation());
    }

    @Test
    public void testParse() {
        // When
        List<Book> books = new DomApi()
                .parse(DomTest.class.getResourceAsStream("/catalog.xml"));
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
        String xml = new DomApi().write(catalog);

        // Then
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><catalog><book isbn10=\"059610149X\"><title>Java and XML</title></book><book isbn10=\"1590597060\"><title>Pro XML Development with Java Technology</title></book></catalog>", xml);
    }

    @Test
    public void testWriteDiffWithXmlUnit() throws SAXException, IOException {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new DomApi().write(catalog);

        // Then
        assertXMLEqual("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog><book isbn10=\"059610149X\">" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<book isbn10=\"1590597060\">" +
                "<title>Pro XML Development with Java Technology</title>" +
                "</book>" +
                "</catalog>", xml);

    }

    @Test
    public void testWriteXpathWithXmlUnit() throws SAXException, IOException, XpathException {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new DomApi().write(catalog);

        // Then
        assertXpathEvaluatesTo("Pro XML Development with Java Technology", "/catalog/book[@isbn10 = '1590597060']/title", xml);
    }

    @Test
    public void testWriteValidateWithXmlUnit() throws SAXException, IOException, XpathException {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new DomApi().write(catalog);

        // Then
        Validator v = new Validator();
        v.addSchemaSource(new StreamSource(DomTest.class.getResourceAsStream("/catalog.xsd")));
        assertTrue("XML must be valid", v.isInstanceValid(new StreamSource(new StringReader(xml))));
    }

    @Test
    public void testWriteXPathWithHamcrest() throws SAXException, IOException, XpathException, ParserConfigurationException {
        // Given
        List<Book> catalog = Arrays.asList(
                createBook("Java and XML", "059610149X"),
                createBook("Pro XML Development with Java Technology", "1590597060"));

        // When
        String xml = new DomApi().write(catalog);

        // Then
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        assertThat(document, hasXPath("/catalog/book[@isbn10 = '1590597060']/title", equalTo("Pro XML Development with Java Technology")));
    }

    private Book createBook(String title, String isbn10) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn10(isbn10);
        return book;
    }
}
