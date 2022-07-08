package server.commander;

public class ExitCommand implements Command {
    @Override
    public String execute() {
        return "OK";
    }
}
