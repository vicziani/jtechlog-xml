package jtechlog.xml.jaxp;

import jtechlog.xml.jaxp.Book;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DomApi {

    public List<Book> parse(InputStream inputStream) {
        List<Book> catalog = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            NodeList l = document.getElementsByTagName("book");
            for (int i = 0; i < l.getLength(); i++) {
                Book book = new Book();
                book.setTitle((((Element)l.item(i)).getElementsByTagName("title").item(0)).getTextContent());
                book.setIsbn10(((Element)l.item(i)).getAttribute("isbn10"));
                catalog.add(book);
            }
            return catalog;
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing xml", e);
        }
    }

    public String write(List<Book> catalog) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("catalog");
            document.appendChild(root);
            for (Book book: catalog) {
                Element bookElement = document.createElement("book");
                root.appendChild(bookElement);
                Attr attr  = document.createAttribute("isbn10");
                attr.setValue(book.getIsbn10());
                bookElement.setAttributeNode(attr);
                Element titleElement = document.createElement("title");
                titleElement.appendChild(document.createTextNode(book.getTitle()));
                bookElement.appendChild(titleElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(baos);

            transformer.transform(source, result);
            return new String(baos.toByteArray());
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing xml", e);
        }
    }
}
