package server.commander;

import server.data.DataSet;

public class DeleteCommand implements Command {
    private final CommandData data;

    public DeleteCommand(CommandData data) {
        this.data = data;
    }

    @Override
    public String execute() {
        if (DataSet.deleteCell(data.getIndex())) {
            return "OK";
        } else {
            return "ERROR";
        }
    }
}
