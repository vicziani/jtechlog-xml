package jtechlog.xml.properties;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class PropertiesTest {

    @Test
    public void testLoadProperties() {
        Properties properties = new PropertiesApi().readProperties(PropertiesTest.class.getResourceAsStream("/jtechlog.properties.xml"));

        assertEquals("JTechLog", properties.getProperty("name"));
    }
}
