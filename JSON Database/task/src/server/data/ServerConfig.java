package server.data;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerConfig {
    private static ServerConfig instance;
    private final String address;
    private final int port;

    private ServerConfig(String address, int port) {
        this.address = address;
        this.port = port;
    }

    private static void initialize() {
        final String fileName = "D:\\7-Learn\\Java\\HyperSkill\\JSON Database\\JSON Database\\task\\src\\resources\\server_conf.json";
        Path path = Paths.get(fileName);
        if (instance == null) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                Gson gson = new Gson();
                instance = gson.fromJson(reader, ServerConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getAddress() {
        initialize();
        return instance.address;
    }

    public static int getPort() {
        initialize();
        return instance.port;
    }
}
