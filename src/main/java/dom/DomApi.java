package dom;

import org.w3c.dom.*;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DomApi {

    public List<Book> parse(InputStream is) {
        List<Book> catalog = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                Document document = documentBuilder.parse(is);
            NodeList l = document.getElementsByTagName("book");
            for (int i = 0; i < l.getLength(); i++) {
                Book book = new Book();
                Element bookElement = (Element) l.item(i);
                book.setTitle((bookElement.getElementsByTagName("title").item(0)).getTextContent());
                book.setIsbn10(bookElement.getAttribute("isbn10"));
                catalog.add(book);
            }
            return catalog;
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing xml", e);
        }
    }

    public List<Book> parseWithIterator(InputStream is) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();

            if (!documentBuilder.getDOMImplementation().hasFeature("traversal", "2.0")) {
                throw new IllegalStateException("Invalid implementation");
            }

            Document document = documentBuilder.parse(is);

            NodeIterator i = ((DocumentTraversal) document).createNodeIterator(document, NodeFilter.SHOW_ELEMENT, null, true);
            Node node;
            List<Book> catalog = new ArrayList<>();
            Book book = null;
            while ((node = i.nextNode()) != null) {
                if (node.getNodeName().equals("book")) {
                    book = new Book();
                    book.setIsbn10(((Element)node).getAttribute("isbn10"));
                    catalog.add(book);
                }
                else if (node.getNodeName().equals("title")) {
                    book.setTitle(node.getTextContent());
                }
            }
            return catalog;
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing xml", e);
        }
    }

    public void write(List<Book> catalog, OutputStream os) {
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
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(os);

            transformer.transform(source, result);
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing xml", e);
        }
    }
}
