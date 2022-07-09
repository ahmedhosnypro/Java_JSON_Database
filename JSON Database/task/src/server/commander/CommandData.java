package server.commander;

import java.util.Objects;

public class CommandData {
    private final String type;
    private final String key;
    private final String value;

    public CommandData(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return type + " " +
                (Objects.nonNull(key) ? " " + key : "") +
                (Objects.nonNull(value) ? " " + value : "");
    }
}
