package server.commander;

public class Controller {
    private Command command;

    public void setCommand(CommandData data) {
        command = switch (data.getType()) {
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