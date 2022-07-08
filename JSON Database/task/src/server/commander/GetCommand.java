package server.commander;

import server.data.DataSet;

public class GetCommand implements Command {
    private final CommandData data;

    public GetCommand(CommandData data) {
        this.data = data;
    }

    @Override
    public String execute() {
        String cell = DataSet.getCellData(data.getIndex());
        if (cell == null) {
            return "ERROR";
        }
        return cell;
    }
}
