package de.murmelmeister.worlds.utils.configs;

public enum Messages {

    PREFIX("Prefix", "&3Worlds &8» &r");

    private String path;
    private String value;

    Messages(String path, String value) {
        setPath(path);
        setValue(value);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
