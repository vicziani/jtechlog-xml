package xslt;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public class XsltApi {

    public String transform(String input) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(
                    new StreamSource(XsltApi.class.getResourceAsStream("/catalog.xslt")));
            StringWriter result = new StringWriter();
            transformer.transform(
                    new StreamSource(new StringReader(input)), new StreamResult(result)
            );
            return result.toString();
        } catch (TransformerException e) {
            throw new RuntimeException("Error transforming xml", e);
        }

    }
}
