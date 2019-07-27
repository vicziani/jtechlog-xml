package jaxbcustom;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Isbn10Adapter extends XmlAdapter<String, Isbn10> {

    public Isbn10 unmarshal(String value) {
        return new Isbn10(value);
    }

    public String marshal(Isbn10 value) {
        return value.getValue();
    }
}
