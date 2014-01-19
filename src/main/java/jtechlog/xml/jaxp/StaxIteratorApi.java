package jtechlog.xml.jaxp;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;

public class StaxIteratorApi {
    public List<Book> parse(Source source) {
        List<Book> catalog = new ArrayList<>();
        try {
            XMLInputFactory f = XMLInputFactory.newInstance();
            XMLEventReader r = f.createXMLEventReader(source);
            Book book = null;
            while (r.hasNext()) {
                XMLEvent event = r.nextEvent();
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    if (event instanceof StartElement) {
                        StartElement element = (StartElement) event;
                        if ("book".equals(element.getName().getLocalPart())) {
                            book = new Book();
                            catalog.add(book);
                            book.setIsbn10(element.getAttributeByName(new QName("isbn10")).getValue());
                        }
                        else if ("title".equals(element.getName().getLocalPart())) {
                            book.setTitle(r.getElementText());
                        }
                    }
                }
            }
        } catch (XMLStreamException xse) {
            throw new RuntimeException("Error writing xml", xse);
        }
        return catalog;
    }
}
