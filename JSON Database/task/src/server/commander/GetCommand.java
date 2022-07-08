package server.commander;

import com.google.gson.Gson;
import server.data.DataSet;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetCommand implements Command {
    private final CommandData data;

    public GetCommand(CommandData data) {
        this.data = data;
    }

    @Override
    public String execute() {
        Gson gson = new Gson();
        Map<String, String> response = new LinkedHashMap<>();
        String cell = DataSet.getCellData(data.getKey());
        if (cell == null) {
            response.put("response", "ERROR");
            response.put("reason", "No such key");
        } else {
            response.put("response", "OK");
            response.put("value", cell);
        }
        return gson.toJson(response);
    }
}
