package jtechlog.xml.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesApi {

    public Properties readProperties(InputStream stream) {
        try {
            Properties properties = new Properties();
            properties.loadFromXML(stream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Error reading properties", e);
        }
    }
}
