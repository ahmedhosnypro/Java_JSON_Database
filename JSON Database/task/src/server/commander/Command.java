package server.commander;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Command {
    static final Gson gson = new Gson();
    static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final String DATA_PATH = "JSON Database/task/src/server/data/";
    //    static final String DATA_PATH = "JSON Database/task/src/server/data/";
    static final String DATA_NAME = DATA_PATH + "db.json";

    abstract String execute();

    static Map readData() {
        Path path = new File(DATA_NAME).toPath();
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
             return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            //ignored
        }
        return new LinkedTreeMap<>();
    }
}