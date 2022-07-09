package server.commander;

import java.util.Map;

public class Controller {
    private Command command;

    public void setCommand(Map<String, Object> data) {
        command = switch (data.get("type").toString()) {
            case "get" -> new GetCommand(data);
            case "set" -> new SetCommand(data);
            case "delete" -> new DeleteCommand(data);
            case "exit" -> new ExitCommand();
            default -> throw new IllegalArgumentException("Unknown command type");
        };
    }

    public String executeCommand() {
        return command.execute();
    }
}