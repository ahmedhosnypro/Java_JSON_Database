package server.commander;

import com.google.gson.Gson;
import server.data.DataSet;

import java.util.LinkedHashMap;
import java.util.Map;

public class SetCommand implements Command {
    private final CommandData data;

    public SetCommand(CommandData data) {
        this.data = data;
    }

    @Override
    public String  execute() {
        Gson gson = new Gson();
        Map<String, String> response = new LinkedHashMap<>();
        if (DataSet.setCell(data.getKey(), data.getValue())) {
            response.put("response", "OK");
        } else {
            response.put("response", "ERROR");
            response.put("reason", "No such key");
        }
        return gson.toJson(response);
    }
}
