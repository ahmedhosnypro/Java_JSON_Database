package server.commander;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GetCommand extends Command {
    private final Map<String, Object> data;

    public GetCommand(Map<String, Object> data) {
        super();
        this.data = data;
    }

    @Override
    public String execute() {
        Gson gson = new Gson();
        Map<String, Object> response = new LinkedHashMap<>();
        var cell = getValue(data);
        if (cell == null) {
            response.put("response", "ERROR");
            response.put("reason", "No such key");
        } else {
            response.put("response", "OK");
            response.put("value", cell);
        }
        return gson.toJson(response);
    }

    private Object getValue(Map<String, Object> commandData) {
        lock.readLock().lock();
        var stringObjectMap = readData();
        lock.readLock().unlock();
        if (stringObjectMap != null) {
            var key = commandData.get("key");
            try {
                List<String> subKeys = (List<String>) key;
                //create queue from an existing string array
                Queue<String> queue = new java.util.LinkedList<>(subKeys);
                Object value = null;
                var subObject = stringObjectMap;
                while (!queue.isEmpty()) {
                    value = getValue(subObject, queue.poll());
                    if (value == null) {
                        break;
                    }
                    try {
                        subObject = (Map<String, Object>) value;
                    } catch (ClassCastException e) {
                        break;
                    }
                }
                return value;
            } catch (ClassCastException e) {
                return getValue(stringObjectMap, (String) commandData.get("key"));
            }
        }
        return null;
    }

    private Object getValue(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
            return data.get(key);
        }
        return null;
    }
}
