package validator;

import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

public class ValidatorApi {

    public boolean validateByXsd(String xml) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(ValidatorApi.class.getResourceAsStream("/catalog.xsd")));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean validateByDtdDom(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            MyErrorHandler errorHandler = new MyErrorHandler();
            builder.setErrorHandler(errorHandler);
            builder.setEntityResolver(new DtdEntityResolver());
            builder.parse(new InputSource(new StringReader(xml)));
            return errorHandler.isValid();
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean validateByDtdSax(String xml) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser saxParser = factory.newSAXParser();
            MyErrorHandler myErrorHandler = new MyErrorHandler();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setErrorHandler(myErrorHandler);
            xmlReader.setEntityResolver(new DtdEntityResolver());

            xmlReader.parse(new InputSource(new StringReader(xml)));
            return myErrorHandler.isValid();
        } catch (Exception e) {
            return false;
        }
    }

    private static class DtdEntityResolver implements EntityResolver {
        @Override
        public InputSource resolveEntity(String publicId, String systemId)  {
            return new InputSource(ValidatorApi.class.getResourceAsStream("/catalog.dtd"));
        }
    }

    private static class MyErrorHandler implements ErrorHandler {

        private boolean valid = true;

        @Override
        public void warning(SAXParseException exception) {
        }

        @Override
        public void error(SAXParseException exception) {
            valid = false;
        }

        @Override
        public void fatalError(SAXParseException exception) {
            valid = false;
        }

        public boolean isValid() {
            return valid;
        }
    }
}
