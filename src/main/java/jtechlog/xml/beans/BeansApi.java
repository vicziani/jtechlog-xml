package jtechlog.xml.beans;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class BeansApi {

    public String writeToXml(Catalog catalog) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder e = new XMLEncoder(baos);
        e.writeObject(catalog);
        e.close();
        return baos.toString();
    }

    public Catalog readFromXml(InputStream stream) {
        XMLDecoder decoder = new XMLDecoder(stream);
        return (Catalog) decoder.readObject();
    }
}
