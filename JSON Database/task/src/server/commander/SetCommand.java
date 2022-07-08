package server.commander;

import server.data.DataSet;

public class SetCommand implements Command {
    private  final CommandData data;

    public SetCommand(CommandData data) {
        this.data = data;
    }

    @Override
    public String execute() {
        if (DataSet.setCell(data.getIndex(), data.getValue())) {
            return "OK";
        } else {
            return "ERROR";
        }
    }
}
