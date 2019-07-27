package properties;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PropertiesTest {

    @Test
    public void testLoadProperties() {
        Properties properties = new PropertiesApi().readProperties(PropertiesTest.class.getResourceAsStream("/jtechlog.properties.xml"));

        assertEquals("JTechLog", properties.getProperty("name"));
    }
}
