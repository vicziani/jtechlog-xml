package beans;

import org.xml.sax.InputSource;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.Reader;

public class BeansApi {

    public String writeToXml(Catalog catalog) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder e = new XMLEncoder(baos);
        e.writeObject(catalog);
        e.close();
        return baos.toString();
    }

    public Catalog readFromXml(Reader reader) {
        XMLDecoder decoder = new XMLDecoder(new InputSource(reader));
        return (Catalog) decoder.readObject();
    }
}
