package de.murmelmeister.worlds.utils.configs;

public enum Configs {

    ;

    private String path;
    private Object value;

    Configs(String path, Object value) {
        setPath(path);
        setValue(value);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
