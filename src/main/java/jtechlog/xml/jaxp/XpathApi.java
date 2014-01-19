package jtechlog.xml.jaxp;

import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

public class XpathApi {

    public String evalAsString(String xml, String xpathExpression) {
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xpathExpression);
            return (String) expr.evaluate(new InputSource(new StringReader(xml)), XPathConstants.STRING);
        }
        catch (Exception e) {
            throw new RuntimeException("Error running xpath", e);
        }
    }
}
