package server.commander;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DeleteCommand extends Command {
    private final Map<String, Object> data;

    public DeleteCommand(Map<String, Object> data) {
        super();
        this.data = data;
    }

    @Override
    public String execute() {
        Gson gson = new Gson();
        Map<String, String> response = new LinkedHashMap<>();
        if (deleteCell(data)) {
            response.put("response", "OK");
        } else {
            response.put("response", "ERROR");
            response.put("reason", "No such key");
        }
        return gson.toJson(response);
    }

    public static boolean deleteCell(Map<String, Object> commandData) {
        var data = readData();
        if (data == null) {
            return false;
        }
        var keys = commandData.get("key");
        var value = commandData.get("value");

        try {
            List<String> keyList = (List<String>) keys;
            Queue<String> queue = new java.util.LinkedList<>(keyList);
            var keyName = queue.poll();
            if (queue.size() == 1) {
                data.put(keyName, value);
            } else {
                Object parentObject = data.getOrDefault(keyName, null);
                if (parentObject == null) {
                    return false;
                }
                while (!queue.isEmpty()) {
                    keyName = queue.poll();
                    var subObject = ((Map<String, Object>) parentObject).getOrDefault(keyName, null);
                    if (subObject == null) {
                        return false;
                    }
                    if (queue.size() > 1) { //sub object is not a string
                        parentObject = subObject;
                    } else {
                        keyName = queue.poll();
                        ((Map<String, Object>) subObject).remove(keyName);
                    }
                }
            }
        } catch (ClassCastException e) {
            if (!data.containsKey((String) keys)) {
                return false;
            } else {
                data.remove((String) keys);
            }
        }

        try (FileWriter writer = new FileWriter(DATA_NAME)) {
            lock.writeLock().lock();

            gson.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        lock.writeLock().unlock();
        return true;
    }
}
