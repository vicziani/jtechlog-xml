package stax;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class StaxIteratorApi {
    public List<Book> parse(Source source) {
        try {
            List<Book> catalog = new ArrayList<>();
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
            return catalog;
        } catch (XMLStreamException xse) {
            throw new RuntimeException("Error writing xml", xse);
        }
    }

    public void write(List<Book> catalog, Writer w) {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEventWriter writer = outputFactory.createXMLEventWriter(w);
            writer.add(eventFactory.createStartDocument());
            writer.add(eventFactory.createStartElement("", "", "catalog"));
            for (Book book: catalog) {
                writer.add(eventFactory.createStartElement("", "", "book"));
                writer.add(eventFactory.createAttribute("isbn10", book.getIsbn10()));
                writer.add(eventFactory.createStartElement("", "", "title"));
                writer.add(eventFactory.createCharacters(book.getTitle()));
                writer.add(eventFactory.createEndElement("", "", "title"));
                writer.add(eventFactory.createEndElement("", "", "book"));
            }
            writer.add(eventFactory.createEndElement("", "", "catalog"));
            writer.add(eventFactory.createEndDocument());
            writer.flush();
        } catch (XMLStreamException xse) {
            throw new RuntimeException("Error writing xml", xse);
        }
    }
}
