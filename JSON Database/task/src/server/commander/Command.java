package server.commander;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Command {
    static final Gson gson = new Gson();
    static final ReadWriteLock lock = new ReentrantReadWriteLock();
     private static final String DATA_PATH = "src/server/data/";
//    static final String DATA_PATH = "JSON Database/task/src/server/data/";
    static final String DATA_NAME = DATA_PATH + "db.json";

    abstract String execute();

    static Map<String, Object> readData() {
        Path path = new File(DATA_NAME).toPath();
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            var ret = gson.fromJson(reader,
                    new TypeToken<Map<String, String>>() {
                    }.getType());

            return (Map<String, Object>) ret;
        } catch (Exception e) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                var ret = gson.fromJson(reader,
                        new TypeToken<Map<String, Object>>() {
                        }.getType());
                return (Map<String, Object>) ret;
            } catch (IOException ignored) {
                //ignored
            }
        }
        return new LinkedTreeMap<>();
    }


    static Map<String, String> fromJson(String json) {
        var ret = gson.fromJson(json,
                new TypeToken<Map<String, String>>() {
                }.getType());
        return (Map<String, String>) ret;
    }
}