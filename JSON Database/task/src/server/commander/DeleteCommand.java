package server.commander;

import com.google.gson.Gson;
import server.data.DataSet;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeleteCommand implements Command {
    private final CommandData data;

    public DeleteCommand(CommandData data) {
        this.data = data;
    }

    @Override
    public String execute() {
        Gson gson = new Gson();
        Map<String, String> response = new LinkedHashMap<>();
        if (DataSet.deleteCell(data.getKey())) {
            response.put("response", "OK");
        } else {
            response.put("response", "ERROR");
            response.put("reason", "No such key");
        }
        return gson.toJson(response);
    }
}
