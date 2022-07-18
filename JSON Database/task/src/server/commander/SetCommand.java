package server.commander;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileWriter;
import java.util.*;

public class SetCommand extends Command {
    private final Map<String, Object> data;

    public SetCommand(Map<String, Object> data) {
        super();
        this.data = data;
    }

    @Override
    public String execute() {
        Gson gson = new Gson();
        Map<String, String> response = new LinkedTreeMap<>();
        if (setData(data)) {
            response.put("response", "OK");
        } else {
            response.put("response", "ERROR");
            response.put("reason", "No such key");
        }
        return gson.toJson(response);
    }

    private boolean setData(Map<String, Object> commandData) {
        var data = readData();
        if (data == null) {
            data = new LinkedTreeMap<>();
        }
        var keys = commandData.get("key");
        var value = commandData.get("value");

        if (keys.getClass() == ArrayList.class) {
            processList(data, (List<String>) keys, value);
        } else {
            data.put((String) keys, value);
        }

        try (FileWriter writer = new FileWriter(DATA_NAME)) {
            lock.writeLock().lock();
            gson.toJson(data, writer);
            lock.writeLock().unlock();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void processList(Map<String, Object> data, List<String> keys, Object value) {
        Queue<String> queue = new LinkedList<>(keys);
        var keyName = queue.poll();
        if (queue.size() == 1) {
            data.put(keyName, value);
        } else {
            Object parentObject = data.getOrDefault(keyName, null);
            if (parentObject == null) {
                parentObject = new LinkedTreeMap<>();
                data.put(keyName, parentObject);
            }
            while (!queue.isEmpty()) {
                keyName = queue.poll();
                var subObject = ((Map<String, Object>) parentObject).getOrDefault(keyName, null);
                if (queue.size() > 1) { //sub object is not a string
                    if (subObject == null) {
                        subObject = new LinkedTreeMap<>();
                        ((Map<String, Object>) parentObject).put(keyName, subObject);
                    } else {
                        parentObject = subObject;
                    }
                } else {
                    keyName = queue.poll();
                    ((Map<String, Object>) subObject).put(keyName, value);
                }
            }
        }
    }
}
