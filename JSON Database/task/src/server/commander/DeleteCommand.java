package server.commander;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.util.*;

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

        if (keys.getClass() == ArrayList.class) {
            if (processList(data, (List<String>) keys, value)) return false;
        } else {
            if (!data.containsKey((String) keys)) {
                return false;
            } else {
                data.remove((String) keys);
            }
        }

        try (FileWriter writer = new FileWriter(DATA_NAME)) {
            lock.writeLock().lock();
            gson.toJson(data, writer);
            lock.writeLock().unlock();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            lock.writeLock().unlock();
            return false;
        }
    }

    private static boolean processList(Map<String, Object> data, List<String> keys, Object value) {
        Queue<String> queue = new LinkedList<>(keys);
        var keyName = queue.poll();
        if (queue.size() == 1) {
            data.put(keyName, value);
        } else {
            Object parentObject = data.getOrDefault(keyName, null);
            if (parentObject == null) {
                return true;
            }
            while (!queue.isEmpty()) {
                keyName = queue.poll();
                var subObject = ((Map<String, Object>) parentObject).getOrDefault(keyName, null);
                if (subObject == null) {
                    return true;
                }
                if (queue.size() > 1) { //sub object is not a string
                    parentObject = subObject;
                } else {
                    keyName = queue.poll();
                    ((Map<String, Object>) subObject).remove(keyName);
                }
            }
        }
        return false;
    }
}
