package server.commander;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExitCommand extends Command {
    @Override
    public String execute() {
        Gson gson = new Gson();
        Map<String, String> response = new LinkedHashMap<>();
        response.put("response", "OK");
        return gson.toJson(response);
    }
}
