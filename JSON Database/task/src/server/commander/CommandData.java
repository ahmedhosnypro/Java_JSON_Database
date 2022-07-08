package server.commander;

import java.util.Objects;

public class CommandData {
    private final String type;
    private final Integer index;
    private final String value;

    public CommandData(String type, Integer index, String value) {
        this.type = type;
        this.index = index;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Integer getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return type + " " +
                (Objects.nonNull(index) ? " " + index : "") +
                (Objects.nonNull(value) ? " " + value : "");
    }
}
