package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SaxApi {

    public List<Book> parse(InputStream inputStream) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BookSaxHandler handler = new BookSaxHandler();
            saxParser.parse(inputStream, handler);
            return handler.getCatalog();
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing xml", e);
        }
    }

    private static class BookSaxHandler extends DefaultHandler {
        private List<Book> catalog = new ArrayList<>();

        private boolean processTitle = false;

        public List<Book> getCatalog() {
            return catalog;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if ("book".equals(qName)) {
                Book book = new Book();
                catalog.add(book);
                book.setIsbn10(attributes.getValue("isbn10"));
            }
            processTitle = "title".equals(qName);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            processTitle = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (processTitle) {
                Book lastBook = catalog.get(catalog.size() - 1);
                lastBook.setTitle(new String(ch, start, length));
            }
        }
    }
}
