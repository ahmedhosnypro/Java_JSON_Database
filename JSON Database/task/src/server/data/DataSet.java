package server.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataSet {
    private DataSet() {
    }

    private static final Map<String, String> DATABASE = new HashMap<>();
    private static final Gson gson = new Gson();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final String DATA_PATH = "src/server/data/";

    public static String getCellData(String key) {
        if (!DATABASE.containsKey(key)) {
            return null;
        }
        lock.readLock().lock();
        readData();
        lock.readLock().unlock();
        return DATABASE.get(key);
    }

    public static boolean setCell(String key, String value) {
        lock.writeLock().lock();
        DATABASE.put(key, value);
        updateData();
        lock.writeLock().unlock();
        return true;
    }

    public static boolean deleteCell(String key) {
        lock.writeLock().lock();
        if (!DATABASE.containsKey(key)) {
            return false;
        }
        DATABASE.remove(key);
        updateData();
        lock.writeLock().unlock();
        return true;
    }

    private static void readData() {

        try (FileReader reader = new FileReader(DATA_PATH + "db.json")) {
            DATABASE.putAll(gson.fromJson(reader,
                    new TypeToken<Map<String, String>>() {
                    }.getType()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateData() {
        try (FileWriter writer = new FileWriter(DATA_PATH + "db.json")) {
            gson.toJson(DATABASE, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
