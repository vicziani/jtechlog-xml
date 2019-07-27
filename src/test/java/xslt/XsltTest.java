package xslt;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.transform.Transformation;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import java.io.StringReader;

public class XsltTest {

    @Test
    public void testXslt() {
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
        String result = new XsltApi().transform(xml);

        // Then
        String expected = "<html>\n" +
                "   <body>\n" +
                "      <h2>Catalog</h2>\n" +
                "      <table>\n" +
                "         <tr>\n" +
                "            <th>ISBN</th>\n" +
                "            <th>Title</th>\n" +
                "         </tr>\n" +
                "         <tr>\n" +
                "            <td>059610149X</td>\n" +
                "            <td>Java and XML</td>\n" +
                "         </tr>\n" +
                "         <tr>\n" +
                "            <td>1590597060</td>\n" +
                "            <td>Pro XML Development with Java Technology</td>\n" +
                "         </tr>\n" +
                "      </table>\n" +
                "   </body>\n" +
                "</html>";

        XmlAssert.assertThat(result).and(expected).ignoreWhitespace().areSimilar();
    }

    @Test
    public void testXsltWithXmlUnit() {
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
        Transformation transformation = new Transformation(new StreamSource(new StringReader(xml)));
        transformation.setStylesheet(new StreamSource(XsltTest.class.getResourceAsStream("/catalog.xslt")));
        String result = transformation.transformToString();

        // Then
        String expected = "<html>\n" +
                "   <body>\n" +
                "      <h2>Catalog</h2>\n" +
                "      <table>\n" +
                "         <tr>\n" +
                "            <th>ISBN</th>\n" +
                "            <th>Title</th>\n" +
                "         </tr>\n" +
                "         <tr>\n" +
                "            <td>059610149X</td>\n" +
                "            <td>Java and XML</td>\n" +
                "         </tr>\n" +
                "         <tr>\n" +
                "            <td>1590597060</td>\n" +
                "            <td>Pro XML Development with Java Technology</td>\n" +
                "         </tr>\n" +
                "      </table>\n" +
                "   </body>\n" +
                "</html>";

        XmlAssert.assertThat(result).and(expected).ignoreWhitespace().areSimilar();
    }
}
