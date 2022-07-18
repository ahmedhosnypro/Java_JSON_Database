package server.commander;

import com.google.gson.Gson;

import java.util.*;

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
        if (stringObjectMap == null) {
            return null;
        }

        var key = commandData.get("key");
        if (key.getClass() == ArrayList.class) {
            return processList(stringObjectMap, (List<String>) key);
        } else {
            return getValue(stringObjectMap, (String) commandData.get("key"));
        }
    }

    private Object processList(Map<String, Object> stringObjectMap, List<String> key) {
        //create queue from an existing string array
        Queue<String> queue = new LinkedList<>(key);
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
    }

    private Object getValue(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
            return data.get(key);
        }
        return null;
    }
}
