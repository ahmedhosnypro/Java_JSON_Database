package client.cli;

public class Command {
    private final String type;
    private final String key;
    private final String value;

    public Command(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }
}
