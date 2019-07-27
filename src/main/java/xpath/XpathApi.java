package xpath;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.xpath.*;
import java.io.StringReader;
import java.util.List;

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

    public boolean validateIsbn(String xml) {
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            xPathfactory.setXPathFunctionResolver(new XPathFunctionResolver() {
                @Override
                public XPathFunction resolveFunction(QName functionName, int arity) {
                    return new XPathFunction() {
                        @Override
                        public Object evaluate(List args) {
                            return new IsbnValidator().validateIsbn10(((NodeList) args.get(0)).item(0).getTextContent());
                        }
                    };
                }
            });
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("tr:findInvalid(/catalog/book[position() = 1]/@isbn10)");
            return (boolean) expr.evaluate(new InputSource(new StringReader(xml)), XPathConstants.BOOLEAN);
        }
        catch (Exception e) {
            throw new RuntimeException("Error running xpath", e);
        }
    }
}
