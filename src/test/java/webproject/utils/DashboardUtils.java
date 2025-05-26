package webproject.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DashboardUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String buildDashboardJson(String description, String name) {
        ObjectNode json = mapper.createObjectNode();

        if (description != null && !description.isBlank()) {
            json.put("description", description);
        }

        if (name != null) {
            json.put("name", name);
        }

        return json.toString();
    }
}
