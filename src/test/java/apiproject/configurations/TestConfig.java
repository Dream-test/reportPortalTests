package apiproject.configurations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("reportportal.properties")) {
            if (input == null) {
                throw new RuntimeException("reportportal.properties not found in resources!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("rp.endpoint");
    }

    public static String getApiKey() {
        return properties.getProperty("rp.api.key");
    }

    public static String getProject() {
        return properties.getProperty("rp.project");
    }

    public static String getAuthHeader() {
        return "Bearer " + getApiKey();
    }
}
