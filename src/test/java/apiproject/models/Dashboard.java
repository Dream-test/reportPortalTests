package apiproject.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Dashboard {

    private String description;
    private String owner;
    private int id;
    private String name;
    private List<Object> widgets;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<Object> widgets) {
        this.widgets = widgets;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

