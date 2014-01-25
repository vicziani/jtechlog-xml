package jtechlog.xml.jaxbinherit;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertEquals;

public class JaxbInheritTest {

    @Test
    public void testParse() {
        // When
        Catalog catalog = new JaxbApi()
                .parse(new StreamSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                        "<catalog><book>" +
                        "<title>Java and XML</title>" +
                        "</book>" + "" +
                        "<magazine>" +
                        "<title>Java Magazine - Oracle</title>" +
                        "</magazine>" +
                        "</catalog>")));
        // Then
        assertEquals(2, catalog.getItems().size());
        assertEquals(Book.class, catalog.getItems().get(0).getClass());
        assertEquals("Java and XML", catalog.getItems().get(0).getTitle());
        assertEquals(Magazine.class, catalog.getItems().get(1).getClass());
    }

    @Test
    public void testWrite() throws IOException, SAXException {
        // Given
        Catalog catalog = createCatalog(
                createBook("Java and XML"),
                createMagazine("Java Magazine - Oracle"));

        // When
        String xml =new JaxbApi().write(catalog);

        // Then
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                "<catalog>" +
                "<book>" +
                "<title>Java and XML</title>" +
                "</book>" + "" +
                "<magazine>" +
                "<title>Java Magazine - Oracle</title>" +
                "</magazine>" +
                "</catalog>", xml);
    }

    private Catalog createCatalog(Item... items) {
        Catalog catalog = new Catalog();
        if (catalog.getItems() == null) {
            catalog.setItems(new ArrayList<Item>());
        }
        for (Item item: items) {
            catalog.getItems().add(item);
        }
        return catalog;
    }

    private Book createBook(String title) {
        Book book = new Book();
        book.setTitle(title);
        return book;
    }

    private Magazine createMagazine(String title) {
        Magazine magazine = new Magazine();
        magazine.setTitle(title);
        return magazine;
    }
}
