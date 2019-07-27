package stax;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class StaxCursorApi {

    public List<Book> parse(Source source) {
        try {
            List<Book> catalog = new ArrayList<>();
            XMLInputFactory f = XMLInputFactory.newInstance();
            XMLStreamReader r = f.createXMLStreamReader(source);
            Book book = null;
            while (r.hasNext()) {                
                if (r.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    if ("book".equals(r.getName().getLocalPart())) {
                        book = new Book();
                        catalog.add(book);
                        book.setIsbn10(r.getAttributeValue(null, "isbn10"));
                    }
                    else if ("title".equals(r.getName().getLocalPart())) {
                        book.setTitle(r.getElementText());
                    }
                }
                r.next();
            }
            return catalog;

        } catch (XMLStreamException xse) {
            throw new RuntimeException("Error parsing xml", xse);
        }
    }

    public void write(List<Book> catalog, Writer w) {
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter streamWriter = output.createXMLStreamWriter(w);
            streamWriter.writeStartDocument();
            streamWriter.writeStartElement("catalog");
            for (Book book: catalog) {
                streamWriter.writeStartElement("book");
                streamWriter.writeAttribute("isbn10", book.getIsbn10());
                streamWriter.writeStartElement("title");
                streamWriter.writeCharacters(book.getTitle());
                streamWriter.writeEndElement();
                streamWriter.writeEndElement();
            }
            streamWriter.writeEndElement();
            streamWriter.flush();
        } catch (XMLStreamException xse) {
            throw new RuntimeException("Error writing xml", xse);
        }
    }

}
