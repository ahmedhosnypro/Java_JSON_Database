package server.data;

import java.util.HashMap;
import java.util.Map;

public class DataSet {
    private DataSet() {
    }

    private static final Map<String, String> DATABASE = new HashMap<>();

    public static String getCellData(String key) {
        if (!DATABASE.containsKey(key)) {
            return null;
        }
        return DATABASE.get(key);
    }

    public static boolean setCell(String key, String value) {
        DATABASE.put(key, value);
        return true;
    }

    public static boolean deleteCell(String key) {
        if (!DATABASE.containsKey(key)) {
            return false;
        }
        DATABASE.remove(key);
        return true;
    }
}
