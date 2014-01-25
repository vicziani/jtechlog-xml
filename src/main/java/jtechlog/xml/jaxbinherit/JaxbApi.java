package jtechlog.xml.jaxbinherit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import java.io.StringWriter;

public class JaxbApi {

    public Catalog parse(Source source) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Catalog.class, Item.class, Book.class, Magazine.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            return (Catalog) unmarshaller.unmarshal(source);
        }
        catch (Exception e) {
            throw new RuntimeException("Error parsing xml", e);
        }
    }

    public String write(Catalog catalog) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Catalog.class, Item.class, Book.class, Magazine.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT,
                    Boolean.FALSE);


            StringWriter writer = new StringWriter();
            marshaller.marshal(catalog, writer);
            return writer.toString();
        }
        catch (Exception e) {
            throw new RuntimeException("Error writing xml", e);
        }
    }
}
